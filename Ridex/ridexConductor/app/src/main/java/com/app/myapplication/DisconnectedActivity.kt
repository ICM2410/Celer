package com.app.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.app.myapplication.databinding.ActivityDisconnectedBinding
import com.app.myapplication.models.Booking
import com.app.myapplication.providers.BookingProvider
import com.app.myapplication.providers.GeoProvider
import com.app.ridexpasajero.providers.AuthProvider
import com.example.easywaylocation.EasyWayLocation
import com.example.easywaylocation.Listener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.ListenerRegistration

class DisconnectedActivity : AppCompatActivity(), OnMapReadyCallback, Listener {

    private var bookingListener: ListenerRegistration? = null
    private lateinit var binding: ActivityDisconnectedBinding

    private var googleMap: GoogleMap? = null;
    var easyWayLocation: EasyWayLocation? = null;
    private var myLocationLatLng: LatLng? = null;
    private var markerDriver: Marker? = null;
    private val geoProvider = GeoProvider();
    private val authProvider = AuthProvider();
    private val bookingProvider = BookingProvider();
    private val modalBooking = BottomSheetOffertActivity();

    private lateinit var sensorManager : SensorManager
    private lateinit var lightSensor : Sensor
    private lateinit var lightEventListener : SensorEventListener

    val timer = object : CountDownTimer(20000, 1000){
        override fun onTick(counter: Long) {
            Log.d("TIMER", "Counter: ${counter}")

        }

        override fun onFinish() {
            Log.d("TIMER", "ON FINISH")
            modalBooking.dismiss()

        }

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisconnectedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager .getDefaultSensor(Sensor.TYPE_LIGHT)!!
        lightEventListener = createLightSensorListener()

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment;
        mapFragment.getMapAsync(this)

        val locationRequest = LocationRequest.create().apply {
            interval = 0
            fastestInterval = 0
            priority = Priority.PRIORITY_HIGH_ACCURACY
            smallestDisplacement = 1f
        }

        easyWayLocation = EasyWayLocation(this, locationRequest, false, false, this )
        locationPermissions.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))

        listenerBooking()

        binding.btnConnect.setOnClickListener{
            connectDriver()

        }
        binding.btnDisconnect.setOnClickListener{
            disconnectDriver()

        }

        binding.btnLogout.setOnClickListener{
            goToMain()
        }

        binding.btnProfile.setOnClickListener{
            val intent = Intent(this, Perfil::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.btnCar.setOnClickListener{
            val intent = Intent(this, CarViewActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.btnConfig.setOnClickListener{

        }

    }

    private fun goToMain(){
        authProvider.logout();
        val intent = Intent(this, SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    val locationPermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){permission ->
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            when{
                permission.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    Log.d("LOCALIZACION", "Permiso Concedido")
                    //easyWayLocation?.startLocation();
                    checkIfDriverIsConnected()

                }
                permission.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    Log.d("LOCALIZACION", "Permiso Concedido con limitacion")
                    checkIfDriverIsConnected()

                }
                else ->{
                    Log.d("LOCALIZACION", "Permiso No Concedido")
                }
            }
        }
    }

    private fun showModalBooking(booking: Booking){
        val bundle = Bundle()
        bundle.putString("booking", booking.toJson())
        modalBooking.arguments = bundle
        modalBooking.isCancelable = false
        modalBooking.show(supportFragmentManager, "BottomSheetOffert2")
        timer.start()
    }

    private fun listenerBooking(){
        bookingListener = bookingProvider.getBooking().addSnapshotListener{
            snapshot, e ->

            if(e != null){
                Log.d("FIRESTORE", "Error ${e.message}")
                return@addSnapshotListener

            }

            if(snapshot != null){
                if(snapshot.documents.size > 0){
                    val booking = snapshot.documents[0].toObject(Booking::class.java)
                    if(booking?.status == "create"){
                        showModalBooking(booking!!)

                    }
                }

            }
        }
    }

    private fun checkIfDriverIsConnected(){
        geoProvider.getLocation(authProvider.getId()).addOnSuccessListener { document ->
            if(document.exists()){
                if (document.contains("l")){
                    connectDriver()
                }
                else{
                    showButtonConnect()
                }
            }
            else{
                showButtonConnect()

            }
        }
    }

    private fun saveLocation(){
        if(myLocationLatLng != null){
            geoProvider.saveLocation(authProvider.getId(), myLocationLatLng!!)

        }
    }

    private fun disconnectDriver(){
        easyWayLocation?.endUpdates()
        if(myLocationLatLng != null){
            geoProvider.removeLocaton(authProvider.getId())
            showButtonConnect()
        }
    }
    private fun connectDriver(){
        easyWayLocation?.endUpdates()
        easyWayLocation?.startLocation()
        showButtonDisconnect()

    }


    private fun showButtonConnect(){
        binding.btnDisconnect.visibility = View.GONE
        binding.btnConnect.visibility = View.VISIBLE
    }
    private fun showButtonDisconnect(){
        binding.btnDisconnect.visibility = View.VISIBLE
        binding.btnConnect.visibility = View.GONE
    }


    private fun addMarker(){
        val drawable = ContextCompat.getDrawable(applicationContext, R.drawable.uber_car)
        val markerIcon = getMarkerFromDrawable(drawable!!)
        if(markerDriver != null){
            markerDriver?.remove()
        }
        if(myLocationLatLng != null){
            markerDriver = googleMap?.addMarker(
                MarkerOptions()
                    .position(myLocationLatLng!!)
                    .anchor(0.5f, 0.5f)
                    .flat(true)
                    .icon(markerIcon)
            )
        }


    }

    private fun getMarkerFromDrawable(drawable: Drawable):BitmapDescriptor{
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(60, 130, Bitmap.Config.ARGB_8888)
        canvas.setBitmap(bitmap)

        drawable.setBounds(0,0,60,130)
        drawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(lightEventListener, lightSensor,
            SensorManager.
            SENSOR_DELAY_NORMAL)
    }

    override fun onDestroy() {
        super.onDestroy()
        easyWayLocation?.endUpdates();
        bookingListener?.remove()

    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap?.uiSettings?.isZoomControlsEnabled = true
        //easyWayLocation?.startLocation();

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        googleMap?.isMyLocationEnabled = false;

        try {
        } catch (e:Resources.NotFoundException){
            Log.d("MAPAS", "Error ${e.toString()}")


        }
    }

    override fun locationOn() {
    }

    override fun currentLocation(location: Location) {
        myLocationLatLng = LatLng(location.latitude, location.longitude)

        googleMap?.moveCamera(CameraUpdateFactory.newCameraPosition(
            CameraPosition.builder().target(myLocationLatLng!!).zoom(17f).build()
        ))

        addMarker()
        saveLocation()
    }

    override fun locationCancelled() {
    }

    fun createLightSensorListener() : SensorEventListener{
        val ret : SensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event != null ) {
                    if (event. values [0] < 5000){
                        googleMap?.setMapStyle(MapStyleOptions.loadRawResourceStyle(baseContext, R.raw.dark_style))
                    } else {
                        googleMap?.setMapStyle(MapStyleOptions.loadRawResourceStyle(baseContext, R.raw.style))
                    }
                }
            }
            override fun onAccuracyChanged(p0: Sensor?, p1: Int) { }

        }
        return ret

    }


    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(lightEventListener)
    }
}