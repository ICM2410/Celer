package com.app.ridexpasajero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.app.ridexpasajero.databinding.ActivityThanksConfirmrideBinding
import com.app.ridexpasajero.models.Booking
import com.app.ridexpasajero.providers.AuthProvider
import com.app.ridexpasajero.providers.BookingProvider
import com.app.ridexpasajero.providers.GeoProvider
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ListenerRegistration
import org.imperiumlabs.geofirestore.callbacks.GeoQueryEventListener

class ThanksConfirmrideActivity : AppCompatActivity() {

    private var listenerBooking: ListenerRegistration? = null
    private lateinit var binding: ActivityThanksConfirmrideBinding
    private var extraOriginName = ""
    private var extraDestinationName = ""
    private var extraOriginLat = 0.0
    private var extraOriginLng = 0.0
    private var extraTime = 15.0
    private var extraDistance = 13.0
    private var extraDestinationLat = 0.0
    private var extraDestinationLng = 0.0

    private var originLatLng: LatLng?=null;
    private var destinationLatLng: LatLng?=null;

    private val geoProvider = GeoProvider()
    private val authProvider = AuthProvider();
    private val bookingProvider = BookingProvider();

    //Busqueda del Conductor
    private var radius = 0.1
    private var idDriver = ""
    private var isDriverFound = false
    private var driverLatLng:LatLng?=null

    private var limitRadius = 20;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThanksConfirmrideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConfirm.setOnClickListener{
            var intent = Intent(baseContext, TripActivity::class.java)
            startActivity(intent)
        }

        //EXTRAS
        extraOriginName = intent.getStringExtra("origin")!!
        extraDestinationName = intent.getStringExtra("destination")!!
        extraOriginLat = intent.getDoubleExtra("origin_lat", 0.0)
        extraOriginLng = intent.getDoubleExtra("origin_lng", 0.0)
        extraDestinationLat = intent.getDoubleExtra("destination_lat", 0.0)
        extraDestinationLng = intent.getDoubleExtra("destination_lng", 0.0)

        originLatLng = LatLng(extraOriginLat, extraOriginLng);
        destinationLatLng = LatLng(extraDestinationLat, extraDestinationLng)

        getClosestDrivers()
        checkIfDriverAccept()

    }

    private fun checkIfDriverAccept(){
        listenerBooking = bookingProvider.getBooking().addSnapshotListener{snapshot, e ->
            if(e != null){
                Log.d("FIRESTORE", "Error ${e.message}")
                return@addSnapshotListener

            }

            if(snapshot != null && snapshot.exists()){
                val booking = snapshot.toObject(Booking::class.java)

                if(booking?.status == "accept"){
                    Toast.makeText(this@ThanksConfirmrideActivity, "Viaje Aceptado", Toast.LENGTH_SHORT).show()

                    listenerBooking?.remove()
                    goToMapTrip()

                }
                else if(booking?.status == "cancel"){
                    Toast.makeText(this@ThanksConfirmrideActivity, "Viaje Cancelado", Toast.LENGTH_SHORT).show()
                    listenerBooking?.remove()

                    goToMap()

                }

            }
        }
    }

    private fun goToMapTrip(){
        val i = Intent(this, OnrideActivity::class.java)
        startActivity(i)
    }

    private fun goToMap(){
        val i = Intent(this, HomeTransportActivity::class.java)
        i.flags =Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
    }

    private fun createBooking(idDriver: String){
        val booking = Booking(
            idClient = authProvider.getId(),
            idDriver = idDriver,
            status = "create",
            destination = extraDestinationName,
            origin = extraOriginName,
            time = extraTime,
            km = extraDistance,
            originLat = extraOriginLat,
            originLng = extraOriginLng,
            destinationLat = extraDestinationLat,
            destinationLng = extraDestinationLng,
            price = 14000.0

        )
        
        bookingProvider.create(booking).addOnCompleteListener { 
            if(it.isSuccessful){
                Toast.makeText(this@ThanksConfirmrideActivity, "Datos del Viaje Creado", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this@ThanksConfirmrideActivity, "Error al crear los datos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getClosestDrivers(){
        geoProvider.getNearByDrivers(originLatLng!!, radius).addGeoQueryEventListener(object : GeoQueryEventListener{

            override fun onKeyEntered(documentID: String, location: GeoPoint) {
                if(!isDriverFound){
                    isDriverFound = true
                    idDriver = documentID
                    driverLatLng = LatLng(location.latitude, location.longitude)
                    binding.textViewSearch.text = "Conductor Encontrado \n Esperando Respuesta"
                    createBooking(documentID)
                }

            }

            override fun onGeoQueryError(exception: Exception) {
            }

            override fun onGeoQueryReady() {

                if(!isDriverFound){
                    radius = radius + 0.1

                    if(radius > limitRadius){
                        binding.textViewSearch.text = "No se encontro ningun conductor"
                    }
                    else{
                        getClosestDrivers()
                    }
                }

            }



            override fun onKeyExited(documentID: String) {
            }

            override fun onKeyMoved(documentID: String, location: GeoPoint) {
            }

        })

    }

    override fun onDestroy() {
        super.onDestroy()
        listenerBooking?.remove()
    }
}