package com.app.myapplication

import android.content.DialogInterface
import android.content.Intent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.app.myapplication.models.Booking
import com.app.myapplication.models.Client
import com.app.myapplication.providers.BookingProvider
import com.app.myapplication.providers.GeoProvider
import com.app.ridexpasajero.providers.AuthProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetOffertActivity : BottomSheetDialogFragment() {

    private lateinit var textViewOrigin: TextView
    private lateinit var textViewDestination: TextView
    private lateinit var textViewTimeDistance: TextView
    private lateinit var btnAccept: Button
    private lateinit var btnRechazar: Button
    private val bookingProvider = BookingProvider();
    private val geoProvider = GeoProvider();
    private val authProvider = AuthProvider();
    private lateinit var mapActivity: DisconnectedActivity

    private lateinit var booking: Booking;


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.bottom_sheet_offert2, container, false);


        textViewOrigin = view.findViewById(R.id.textOrigin);
        textViewDestination = view.findViewById(R.id.textDestiny);
        textViewTimeDistance = view.findViewById(R.id.textTimeDistance);
        btnAccept = view.findViewById(R.id.btnConfirmRequest);
        btnRechazar = view.findViewById(R.id.btnCancel);

        val data = arguments?.getString("booking")
        booking = Booking.fromJson(data!!)!!

        textViewOrigin.text = booking?.origin
        textViewDestination.text = booking?.destination
        textViewTimeDistance.text = "${booking?.time} min - ${booking?.km} km"

        btnAccept.setOnClickListener {
            aceptarBooking(booking?.idClient!!)
        }

        btnRechazar.setOnClickListener {
            cancelarBooking(booking?.idClient!!)
        }

        return view
    }

    private fun aceptarBooking(idClient: String){

        bookingProvider.updateStatus(idClient, "accept").addOnCompleteListener {
            if(it.isSuccessful){
                (activity as? DisconnectedActivity)?.easyWayLocation?.endUpdates()
                geoProvider.removeLocaton(authProvider.getId())
                goToMapTrip()
            }
            else{
                if(context != null){
                    Toast.makeText(context, "No se pudo aceptar el viaje", Toast.LENGTH_SHORT).show()

                }

            }
        }

    }

    private fun goToMapTrip(){
        val i = Intent(context, ConnectedActivity::class.java)
        context?.startActivity(i)
    }


    private fun cancelarBooking(idClient: String){

        bookingProvider.updateStatus(idClient, "cancel").addOnCompleteListener {
            if(it.isSuccessful){
                dismiss()

                if(context != null){
                    Toast.makeText(context, "Viaje Cancelado", Toast.LENGTH_SHORT).show()

                }
            }
            else{
                if(context != null){
                    Toast.makeText(context, "No se pudo cancelar el viaje", Toast.LENGTH_SHORT).show()

                }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setOnClickListener{
            dismiss()
        }
        /*val aceptar: Button = view.findViewById(R.id.aceptar)
        val cancelar: Button = view.findViewById(R.id.cancelar)
        aceptar.setOnClickListener {
            dismiss()
            val intent = Intent(activity,ConnectedActivity::class.java)
            startActivity(intent)
        }
        cancelar.setOnClickListener {
            dismiss()
            val intent = Intent(activity, RideCancelActivity::class.java)
            startActivity(intent)
        }*/
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if(booking.idClient != null){
            cancelarBooking(booking.idClient!!)
        }
    }
}