package com.app.ridexpasajero

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
import com.app.ridexpasajero.databinding.ActivityOnrideBinding
import com.app.ridexpasajero.models.Booking
import com.app.ridexpasajero.providers.BookingProvider
import com.app.ridexpasajero.providers.GeoProvider
import com.app.ridexpasajero.providers.AuthProvider
import com.app.ridexpasajero.utils.CarMoveAnim
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
import com.google.firebase.firestore.toObject

class OnrideActivity : AppCompatActivity(), OnMapReadyCallback, Listener, DirectionUtil.DirectionCallBack {


    private var driverLocation: LatLng? = null
    private var endLatLng: LatLng? = null
    private var startLatLng: LatLng? = null

    private var listenerBooking: ListenerRegistration?=null

    private var originLatLng: LatLng? = null
    private var destinationLatLng: LatLng? = null

    private var booking: Booking? = null
    private var markerOrigin: Marker? = null
    private var markerDestination: Marker? = null
    private var bookingListener: ListenerRegistration? = null
    private lateinit var binding: ActivityOnrideBinding

    private var googleMap: GoogleMap? = null;
    var easyWayLocation: EasyWayLocation? = null;
    private var myLocationLatLng: LatLng? = null;
    private var markerDriver: Marker? = null;
    private val geoProvider = GeoProvider();
    private val authProvider = AuthProvider();
    private val bookingProvider = BookingProvider();

    private var wayPoints: ArrayList<LatLng> = ArrayList();
    private val WAY_POINT_TAG = "way_point_tag";
    private lateinit var directionUtil: DirectionUtil

    private var isLocationEnabled = false;
    private var isCloseToOrigin = false;
    private var isDriverLocationFound = false;
    private var isBookingLoaded = false;

    private var modalTrip = BottomSheetTrip();



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnrideBinding.inflate(layoutInflater)
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

        binding.btnInfo.setOnClickListener{showModalInfo()}





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

                }
                permission.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    Log.d("LOCALIZACION", "Permiso Concedido con limitacion")

                }
                else ->{
                    Log.d("LOCALIZACION", "Permiso No Concedido")
                }
            }
        }
    }

    private fun getLocationDriver(){

        if(booking != null){
            geoProvider.getLocationWorking(booking?.idDriver!!).addSnapshotListener{document, e ->
                if(e != null){
                    Log.d("FIRESTORE", "Error ${e.message}")
                    return@addSnapshotListener

                }

                if(driverLocation != null){
                    endLatLng = driverLocation
                }

                var l = document?.get("l")
                Log.d("FIRESTORE", "LOCATION L:$l")
                var list: List<Double>? = document?.get("l") as List<Double>?
                Log.d("FIRESTORE", "LOCATION list:$list")
                val lat: Double? = list?.get(0)
                val lng: Double? = list?.get(1) as Double?
                Log.d("FIRESTORE", "LOCATION lat: $lat")



                if(l!=null){
                    driverLocation = LatLng(lat!!, lng!!)

                    markerDriver?.remove()
                    addDriverMarker(driverLocation!!)
                    if(!isDriverLocationFound){
                        isDriverLocationFound = true
                        addDriverMarker(driverLocation!!)
                        easyDrawRoute(driverLocation!!, originLatLng!!)
                    }

                }




                if(endLatLng != null){
                    CarMoveAnim.carAnim(markerDriver!!, endLatLng!!, driverLocation!!)
                }

            }

        }

    }

    private fun getBooking(){
        listenerBooking = bookingProvider.getBooking().addSnapshotListener{document, e ->
            if(e != null){
                Log.d("FIRESTORE", "Error ${e.message}")
                return@addSnapshotListener

            }


            booking = document?.toObject(Booking::class.java)


            if(!isBookingLoaded){
                isBookingLoaded = true
                originLatLng = LatLng(booking?.originLat!!, booking?.originLng!!)
                destinationLatLng = LatLng(booking?.destinationLat!!, booking?.destinationLng!!)
                googleMap?.moveCamera(CameraUpdateFactory.newCameraPosition(
                    CameraPosition.builder().target(originLatLng!!).zoom(17f).build()
                ))
                getLocationDriver()
                addOriginMarker(originLatLng!!)
            }

            if(booking?.status == "accept"){
                binding.textViewStatus.text = "Aceptado"
            }
            else if(booking?.status == "started"){
                startTrip()

            }
            else if(booking?.status == "finished"){
                finishTrip()
            }


        }
    }

    private fun finishTrip(){
        binding.textViewStatus.text = "Finalizado"
        val intent = Intent(this, HomeTransportActivity::class.java);
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun startTrip(){
        binding.textViewStatus.text = "Iniciado"
        googleMap?.clear()
        if(driverLocation != null){
            addDriverMarker(driverLocation!!)
            addDestinationMarker();
            easyDrawRoute(driverLocation!!, destinationLatLng!!)

        }
    }

    private fun easyDrawRoute(originLatLng: LatLng, destinationLatLng: LatLng){
        wayPoints.clear()
        wayPoints.add(originLatLng!!)
        wayPoints.add(destinationLatLng!!)

        directionUtil = DirectionUtil.Builder().setDirectionKey(resources.getString(R.string.google_maps_key))
            .setOrigin(originLatLng!!)
            .setWayPoints(wayPoints)
            .setGoogleMap(googleMap!!)
            .setPolyLinePrimaryColor(R.color.greenPrimary)
            .setPolyLineWidth(10)
            .setPathAnimation(true)
            .setCallback(this)
            .setDestination(destinationLatLng)
            .build()

        directionUtil.initPath()


    }

    private fun addOriginMarker(position: LatLng){
        markerOrigin =googleMap?.addMarker(MarkerOptions().position(position!!).title("Recoger Aqui").icon(BitmapDescriptorFactory.fromResource(R.drawable.icons_location_person)))
    }

    private fun addDriverMarker(position: LatLng){
        markerDriver =googleMap?.addMarker(MarkerOptions().position(position!!).title("Tu conductor").icon(BitmapDescriptorFactory.fromResource(R.drawable.uber_car)))
    }

    private fun addDestinationMarker(){
        if(destinationLatLng != null){
            markerDestination =googleMap?.addMarker(MarkerOptions().position(destinationLatLng!!).title("Recoger Aqui").icon(BitmapDescriptorFactory.fromResource(R.drawable.icons_pin)))

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
        listenerBooking?.remove()

    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap?.uiSettings?.isZoomControlsEnabled = true

        getBooking()
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



    override fun locationOn() {
    }

    override fun currentLocation(location: Location) {



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