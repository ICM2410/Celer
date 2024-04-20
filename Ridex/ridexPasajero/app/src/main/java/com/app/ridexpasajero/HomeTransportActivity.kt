package com.app.ridexpasajero

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources

import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.app.ridexpasajero.databinding.ActivityHomeTransportBinding
import com.app.ridexpasajero.providers.GeoProvider
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

class HomeTransportActivity : AppCompatActivity(), OnMapReadyCallback, Listener {
    private  lateinit var binding: ActivityHomeTransportBinding

    private var googleMap: GoogleMap? = null;
    private var easyWayLocation: EasyWayLocation? = null;
    private var myLocationLatLng: LatLng? = null;
    private var markerDriver: Marker? = null;
    private val geoProvider = GeoProvider();
    private val authProvider = AuthProvider();
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


        //val bottomSheet = BottomSheetFragmentLocation();

        /*binding.btnShowSlideUp.setOnClickListener{
            bottomSheet.show(supportFragmentManager, "BottomSheetSelectRide")

        }

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

        }*/

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
        googleMap?.isMyLocationEnabled = true;

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

        googleMap?.moveCamera(
            CameraUpdateFactory.newCameraPosition(
            CameraPosition.builder().target(myLocationLatLng!!).zoom(17f).build()
        ))

    }

    override fun locationCancelled() {
    }
}