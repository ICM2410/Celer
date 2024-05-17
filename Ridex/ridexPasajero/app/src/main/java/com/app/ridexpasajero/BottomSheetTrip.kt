package com.app.ridexpasajero

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.app.ridexpasajero.models.Booking
import com.app.ridexpasajero.models.Client
import com.app.ridexpasajero.models.Driver
import com.app.ridexpasajero.providers.AuthProvider
import com.app.ridexpasajero.providers.ClientProvider
import com.app.ridexpasajero.providers.DriverProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetTrip :  BottomSheetDialogFragment() {

    private lateinit var booking: Booking
    private var client:Driver?=null

    private val authProvider = AuthProvider();
    private val driverProvider = DriverProvider();
    var textViewName: TextView? = null
    var textViewOrigin: TextView? = null
    var textViewBrand: TextView? = null
    var textViewModelo: TextView? = null
    var textViewPlaca: TextView? = null
    var textViewDestination: TextView? = null
    var imageViewClient: ImageView? = null
    var imageViewPhone: ImageView?=null
    var imageViewCar: ImageView?=null
    var imageViewChat: ImageView?=null

    val REQUEST_PHONE_CALL = 30


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.bottom_sheet_trip, container, false);

        textViewName = view.findViewById(R.id.textName)
        textViewOrigin = view.findViewById(R.id.textOrigin)
        textViewDestination = view.findViewById(R.id.textDestiny)
        imageViewClient = view.findViewById(R.id.imageCircleView)
        imageViewPhone = view.findViewById(R.id.imageCall)
        imageViewCar = view.findViewById(R.id.imageCar)
        textViewBrand =view.findViewById(R.id.textCarBrand)
        textViewModelo=view.findViewById(R.id.textModelo)
        textViewPlaca=view.findViewById(R.id.textPlaca)
        imageViewPhone = view.findViewById(R.id.imageCall)
        imageViewChat = view.findViewById(R.id.imageChat)

        val data = arguments?.getString("booking")
        booking = Booking.fromJson(data!!)!!

        textViewOrigin?.text = booking.origin
        textViewDestination?.text = booking.destination

        imageViewPhone?.setOnClickListener {
            if(client?.phone != null){
                if(ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CALL_PHONE),REQUEST_PHONE_CALL )
                }

                call(client?.phone!!)
            }
        }

        imageViewChat?.setOnClickListener {
            val intent = Intent(requireActivity(), ChatActivity2::class.java)
            if(booking.id != null){
                intent.putExtra("idBooking",booking.id);

            }
            startActivity(intent)
        }

        getClient()


        return view
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == REQUEST_PHONE_CALL){
            if(client?.phone != null){
                call(client?.phone!!)
            }
        }
    }

    private fun call(phone: String){
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:${phone}")

        if(ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            return
        }
        requireActivity().startActivity(intent)
    }



    private fun getClient(){
        driverProvider.getDriver(booking?.idDriver!!).addOnSuccessListener { document ->
            if(document.exists()){
                client = document.toObject(Driver::class.java)
                textViewName?.text = client?.name

                if(client?.image != null){
                    if(client?.image != ""){
                        Glide.with(requireActivity()).load(client?.image).into(imageViewClient!!)
                    }
                }

                if(client?.imageCar != null){
                    if(client?.imageCar != ""){
                        Glide.with(requireActivity()).load(client?.imageCar).into(imageViewCar!!)
                    }
                }

                if(client?.brandcar != null){
                    if(client?.brandcar != ""){
                        textViewBrand?.text = client?.brandcar;
                    }
                }

                if(client?.placa != null){
                    if(client?.placa != ""){
                        textViewPlaca?.text = client?.placa;
                    }
                }

                if(client?.modelo != null){
                    if(client?.modelo != ""){
                        textViewModelo?.text = client?.modelo;
                    }
                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setOnClickListener{
            dismiss()
        }

    }
}