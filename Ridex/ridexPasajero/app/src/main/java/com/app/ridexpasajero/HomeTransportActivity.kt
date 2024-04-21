package com.app.ridexpasajero

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Geocoder

import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.app.ridexpasajero.databinding.ActivityHomeTransportBinding
import com.app.ridexpasajero.models.DriverLocation
import com.app.ridexpasajero.providers.GeoProvider
import com.app.ridexpasajero.providers.AuthProvider
import com.app.ridexpasajero.utils.CarMoveAnim
import com.example.easywaylocation.EasyWayLocation
import com.example.easywaylocation.Listener
import com.google.android.gms.common.api.Status
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
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.firebase.firestore.GeoPoint
import com.google.maps.android.SphericalUtil
import org.imperiumlabs.geofirestore.callbacks.GeoQueryEventListener

class HomeTransportActivity : AppCompatActivity(), OnMapReadyCallback, Listener {
    private  lateinit var binding: ActivityHomeTransportBinding

    private var googleMap: GoogleMap? = null;
    private var easyWayLocation: EasyWayLocation? = null;
    private var myLocationLatLng: LatLng? = null;
    private var markerDriver: Marker? = null;
    private val geoProvider = GeoProvider();
    private val authProvider = AuthProvider();

    private var places: PlacesClient? = null
    private var autoCompleteOrigin: AutocompleteSupportFragment? = null
    private var autoCompleteDestination: AutocompleteSupportFragment? = null
    private var originName = ""
    private var destinationName = ""
    private var originLatLng: LatLng? = null
    private var destinationLatLng: LatLng? = null

    private var isLocationEnabled = false

    private val driversMarkers = ArrayList<Marker>();

    private val driverLocations = ArrayList<DriverLocation>();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeTransportBinding.inflate(layoutInflater)
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

        startGooglePlaces()

        binding.btnWallet.setOnClickListener{
            var intent = Intent(baseContext, WalletActivity::class.java)
            startActivity(intent)
        }

        binding.btnHistorial.setOnClickListener{
            var intent = Intent(baseContext, HistoryUpcoming::class.java)
            startActivity(intent)
        }

        binding.btnProfile.setOnClickListener{
            var intent = Intent(baseContext, ProfileActivity::class.java)
            startActivity(intent)

        }

        binding.btnConfig.setOnClickListener{
            var intent = Intent(baseContext, SettingsMain::class.java)
            startActivity(intent)

        }

        //val bottomSheet = BottomSheetFragmentLocation();

        /*binding.btnShowSlideUp.setOnClickListener{
            bottomSheet.show(supportFragmentManager, "BottomSheetSelectRide")

        }

        */

    }

    val locationPermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permission ->
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            when{
                permission.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    Log.d("LOCALIZACION", "Permiso Concedido")
                    easyWayLocation?.startLocation();

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

    private fun getNearbyDrivers(){

        if(myLocationLatLng == null) return

        geoProvider.getNearByDrivers(myLocationLatLng!!, 30.0).addGeoQueryEventListener(object : GeoQueryEventListener{

            override fun onKeyEntered(documentID: String, location: GeoPoint) {
                for(marker in driversMarkers){
                    if (marker.tag != null){
                        if(marker.tag == documentID){
                            return
                        }
                    }
                }
                val driverLatLng = LatLng(location.latitude, location.longitude)
                val marker = googleMap?.addMarker(
                    MarkerOptions().position(driverLatLng).title("Conductor Disponible").icon(BitmapDescriptorFactory.fromResource(R.drawable.uber_car))
                )
                marker?.tag = documentID
                driversMarkers.add(marker!!)

                val dl = DriverLocation()
                dl.id = documentID
                driverLocations.add(dl)
            }

            override fun onKeyExited(documentID: String) {
                for(marker in driversMarkers){
                    if(marker.tag != null){
                        if(marker.tag == documentID){
                            marker.remove()
                            driversMarkers.remove(marker)
                            driverLocations.removeAt(getPositionDriver(documentID))
                            return
                        }
                    }
                }
            }

            override fun onKeyMoved(documentID: String, location: GeoPoint) {

                for(marker in driversMarkers){

                    val start = LatLng(location.latitude, location.latitude)
                    var end: LatLng? = null
                    val position = getPositionDriver(marker.tag.toString())

                    if(marker.tag != null){
                        if(marker.tag == documentID){
                            //marker.position = LatLng(location.latitude, location.longitude)
                            if(driverLocations[position].latlng !=null){
                                end = driverLocations[position].latlng
                            }
                            driverLocations[position].latlng = LatLng(location.latitude, location.longitude)

                            if(end != null){
                                CarMoveAnim.carAnim(marker,end,start)
                            }
                        }
                    }
                }

            }

            override fun onGeoQueryError(exception: Exception) {

            }

            override fun onGeoQueryReady() {
            }






        })

    }

    private fun getPositionDriver(id:String): Int{
        var position = 0
        for(i in driverLocations.indices){
            if(id == driverLocations[i].id){
                position = i
                break
            }
        }

        return position
    }

    private fun onCameraMove(){
        googleMap?.setOnCameraIdleListener {
            try {
                val geocoder = Geocoder(this)
                originLatLng = googleMap?.cameraPosition?.target
                val addressList = geocoder.getFromLocation(originLatLng?.latitude!!, originLatLng?.longitude!!, 1)
                val city = addressList?.get(0)?.locality
                val address = addressList?.get(0)?.getAddressLine(0)

                if (!city.isNullOrEmpty() && !address.isNullOrEmpty()) {

                    originName = "$address, $city"

                    autoCompleteOrigin?.setText("$address, $city")

                } else if (!city.isNullOrEmpty()) {

                    originName = city

                    autoCompleteOrigin?.setText(city)

                } else if (!address.isNullOrEmpty()) {

                    originName = address

                    autoCompleteOrigin?.setText(address)

                }


            } catch (e: Exception){
                Log.d("ERROR", "Error: ${e.message}")
            }
        }
    }

    private fun startGooglePlaces(){
        if(!Places.isInitialized()){
            Places.initialize(applicationContext, resources.getString(R.string.google_maps_key))

        }
        places = Places.createClient(this)
        instanceAutocompleteOrigin()
        instanceAutocompleteDestination()
    }

    private fun limitSearch(){
        val northSide = SphericalUtil.computeOffset(myLocationLatLng, 5000.0, 0.0)
        val southSide = SphericalUtil.computeOffset(myLocationLatLng, 5000.0, 180.0)
        autoCompleteOrigin?.setLocationBias(RectangularBounds.newInstance(southSide, northSide))
        autoCompleteDestination?.setLocationBias(RectangularBounds.newInstance(southSide, northSide))
    }

    private fun instanceAutocompleteOrigin(){
        autoCompleteOrigin = supportFragmentManager.findFragmentById(R.id.placesAutocompleteOrigin) as AutocompleteSupportFragment
        autoCompleteOrigin?.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS
            )

        )
        autoCompleteOrigin?.setHint("Lugar de recogida")
        autoCompleteOrigin?.setCountry("CO")
        autoCompleteOrigin?.setOnPlaceSelectedListener(object: PlaceSelectionListener{
            override fun onError(p0: Status) {
            }

            override fun onPlaceSelected(place: Place) {
                originName = place.name!!
                originLatLng = place.latLng
                Log.d("PLACES", "Address: ${originName}")
                Log.d("PLACES", "Lat: ${originLatLng?.latitude} ")
                Log.d("PLACES", "LNG: ${originLatLng?.longitude} ")


            }
        })
    }

    private fun instanceAutocompleteDestination(){
        autoCompleteDestination = supportFragmentManager.findFragmentById(R.id.placesAutocompleteDestination) as AutocompleteSupportFragment
        autoCompleteDestination?.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS
            )

        )
        autoCompleteDestination?.setHint("Destino")
        autoCompleteDestination?.setCountry("CO")
        autoCompleteDestination?.setOnPlaceSelectedListener(object: PlaceSelectionListener{
            override fun onError(p0: Status) {
            }

            override fun onPlaceSelected(place: Place) {
                destinationName = place.name!!
                destinationLatLng = place.latLng
                Log.d("PLACES", "Address: ${destinationName}")
                Log.d("PLACES", "Lat: ${destinationLatLng?.latitude} ")
                Log.d("PLACES", "LNG: ${destinationLatLng?.longitude} ")

            }
        })
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onDestroy() {
        super.onDestroy()
        easyWayLocation?.endUpdates();

    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap?.uiSettings?.isZoomControlsEnabled = true
        onCameraMove()
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


        } catch (e: Resources.NotFoundException){
            Log.d("MAPAS", "Error ${e.toString()}")


        }
    }

    override fun locationOn() {
    }

    override fun currentLocation(location: Location) {
        myLocationLatLng = LatLng(location.latitude, location.longitude)



        if(!isLocationEnabled){
            isLocationEnabled = true
            googleMap?.moveCamera(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition.builder().target(myLocationLatLng!!).zoom(15f).build()
                ))
            getNearbyDrivers()
            limitSearch()
        }

    }

    override fun locationCancelled() {
    }
}