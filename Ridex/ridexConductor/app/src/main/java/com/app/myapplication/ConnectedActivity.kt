package com.app.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.app.myapplication.databinding.ActivityConnectedBinding
import com.app.myapplication.databinding.ActivityDisconnectedBinding
import com.app.myapplication.models.Booking
import com.app.myapplication.providers.BookingProvider
import com.app.myapplication.providers.GeoProvider
import com.app.ridexpasajero.providers.AuthProvider
import com.example.easywaylocation.EasyWayLocation
import com.example.easywaylocation.Listener
import com.example.easywaylocation.draw_path.DirectionUtil
import com.example.easywaylocation.draw_path.PolyLineDataBean
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

class ConnectedActivity : AppCompatActivity(), OnMapReadyCallback, Listener, DirectionUtil.DirectionCallBack {

    private var originLatLng: LatLng? = null
    private var destinationLatLng: LatLng? = null

    private var booking: Booking? = null
    private var markerOrigin: Marker? = null
    private var markerDestination: Marker? = null
    private var bookingListener: ListenerRegistration? = null
    private lateinit var binding: ActivityConnectedBinding

    private var googleMap: GoogleMap? = null;
    var easyWayLocation: EasyWayLocation? = null;
    private var myLocationLatLng: LatLng? = null;
    private var markerDriver: Marker? = null;
    private val geoProvider = GeoProvider();
    private val authProvider = AuthProvider();
    private val bookingProvider = BookingProvider();
    private val modalBooking = BottomSheetOffertActivity();

    private var wayPoints: ArrayList<LatLng> = ArrayList();
    private val WAY_POINT_TAG = "way_point_tag";
    private lateinit var directionUtil: DirectionUtil

    private var isLocationEnabled = false;
    private var isCloseToOrigin = true;

    private var modalTrip = BottomSheetTripActivity();


    private val timer = object : CountDownTimer(20000, 1000){
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
        binding = ActivityConnectedBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        binding.btnStartTrip.setOnClickListener{updateToStarted()}
        binding.btnFinishTrip.setOnClickListener{updateToFinish()}
        binding.btnInfo.setOnClickListener{showModalInfo()}

       /* binding.btnConnect.setOnClickListener{
            connectDriver()

        }
        binding.btnDisconnect.setOnClickListener{
            disconnectDriver()

        }*/

    }

    private fun showModalInfo(){

        if(booking != null){
            val bundle = Bundle()
            bundle.putString("booking", booking?.toJson())
            modalTrip.arguments = bundle
            modalTrip.isCancelable = false
            modalTrip.show(supportFragmentManager, "BottomSheetTripInfo")
        }
        else{
            Toast.makeText(this, "No se pudo cargar la informacion.", Toast.LENGTH_SHORT).show()
        }


    }

    val locationPermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){permission ->
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            when{
                permission.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    Log.d("LOCALIZACION", "Permiso Concedido")
                    easyWayLocation?.startLocation();

                }
                permission.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    Log.d("LOCALIZACION", "Permiso Concedido con limitacion")
                    easyWayLocation?.startLocation();

                }
                else ->{
                    Log.d("LOCALIZACION", "Permiso No Concedido")
                }
            }
        }
    }

    private fun getDistanceBetween(originLatLng: LatLng, destinationLatLng: LatLng): Float{

        var distance = 0.0f
        val originLocation = Location("")
        val destinationLocation = Location("")

        originLocation.latitude = originLatLng.latitude
        originLocation.longitude = originLatLng.longitude

        destinationLocation.latitude = destinationLatLng.latitude
        destinationLocation.longitude = destinationLatLng.longitude

        distance = originLocation.distanceTo(destinationLocation)
        return  distance

    }

    private fun getBooking(){
        bookingProvider.getBooking().get().addOnSuccessListener { query ->
            if(query != null){
                if(query.size() > 0){
                    booking = query.documents[0].toObject(Booking::class.java)
                    originLatLng = LatLng(booking?.originLat!!, booking?.originLng!!)
                    destinationLatLng = LatLng(booking?.destinationLat!!, booking?.destinationLng!!)
                    easyDrawRoute(originLatLng!!)
                    addOriginMarker(originLatLng!!)
                }
            }
        }
    }

    private fun easyDrawRoute(position: LatLng){
        wayPoints.clear()
        wayPoints.add(myLocationLatLng!!)
        wayPoints.add(position!!)

        directionUtil = DirectionUtil.Builder().setDirectionKey(resources.getString(R.string.google_maps_key))
            .setOrigin(myLocationLatLng!!)
            .setWayPoints(wayPoints)
            .setGoogleMap(googleMap!!)
            .setPolyLinePrimaryColor(R.color.greenPrimary)
            .setPolyLineWidth(10)
            .setPathAnimation(true)
            .setCallback(this)
            .setDestination(position)
            .build()

        directionUtil.initPath()


    }

    private fun addOriginMarker(position: LatLng){
        markerOrigin =googleMap?.addMarker(MarkerOptions().position(position!!).title("Recoger Aqui").icon(BitmapDescriptorFactory.fromResource(R.drawable.icons_location_person)))
    }

    private fun addDestinationMarker(){
        if(destinationLatLng != null){
            markerDestination =googleMap?.addMarker(MarkerOptions().position(destinationLatLng!!).title("Recoger Aqui").icon(BitmapDescriptorFactory.fromResource(R.drawable.icons_pin)))

        }
    }

    private fun showModalBooking(booking: Booking){
        val bundle = Bundle()
        bundle.putString("booking", booking.toJson())
        modalBooking.arguments = bundle

        modalBooking.show(supportFragmentManager, "BottomSheetOffert2")
        timer.start()
    }



    private fun saveLocation(){
        if(myLocationLatLng != null){
            geoProvider.saveLocationWorking(authProvider.getId(), myLocationLatLng!!)

        }
    }

    private fun disconnectDriver(){
        easyWayLocation?.endUpdates()
        if(myLocationLatLng != null){
            geoProvider.removeLocaton(authProvider.getId())
        }
    }


    private fun showButtonFinish(){
        binding.btnStartTrip.visibility = View.GONE
        binding.btnFinishTrip.visibility = View.VISIBLE
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



    override fun onDestroy() {
        super.onDestroy()
        easyWayLocation?.endUpdates();

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
            val success = googleMap?.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(this, R.raw.style)

            )
            if(!success!!){
                Log.d("MAPAS", "No se pudo encontrar el estilo")

            }


        } catch (e:Resources.NotFoundException){
            Log.d("MAPAS", "Error ${e.toString()}")


        }
    }

    private fun updateToStarted(){
        if(isCloseToOrigin){
            bookingProvider.updateStatus(booking?.idClient!!, "started").addOnCompleteListener {
                if(it.isSuccessful){
                    if(destinationLatLng != null){
                        googleMap?.clear()
                        addMarker()
                        easyDrawRoute(destinationLatLng!!)
                        markerOrigin?.remove()
                        addDestinationMarker()

                    }
                    showButtonFinish()
                }
            }
        }else{
            Toast.makeText(this, "Debes estar mas cerca a la posicion de recogida", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateToFinish(){
            bookingProvider.updateStatus(booking?.idClient!!, "finished").addOnCompleteListener {
                if(it.isSuccessful){
                    val intent = Intent(this, DisconnectedActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
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

        if(booking != null && originLatLng != null){
            var distance = getDistanceBetween(myLocationLatLng!!, originLatLng!!)
            isCloseToOrigin = true

        }

        if(!isLocationEnabled){
            isLocationEnabled = true
            getBooking()

        }


    }

    override fun locationCancelled() {
    }

    override fun pathFindFinish(
        polyLineDetailsMap: HashMap<String, PolyLineDataBean>,
        polyLineDetailsArray: ArrayList<PolyLineDataBean>
    ) {
        directionUtil.drawPath(WAY_POINT_TAG)

    }
}