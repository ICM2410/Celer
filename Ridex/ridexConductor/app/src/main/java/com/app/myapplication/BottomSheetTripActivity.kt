package com.app.myapplication

import android.Manifest
import android.app.Dialog
import android.content.DialogInterface
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
import com.app.myapplication.models.Booking
import com.app.myapplication.models.Client
import com.app.myapplication.providers.BookingProvider
import com.app.myapplication.providers.GeoProvider
import com.app.ridexpasajero.providers.AuthProvider
import com.app.ridexpasajero.providers.ClientProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialog

class BottomSheetTripActivity : BottomSheetDialogFragment() {

    private lateinit var booking: Booking
    private var client:Client?=null

    private val authProvider = AuthProvider();
    private val clientProvider = ClientProvider();
    var textViewName: TextView? = null
    var textViewOrigin: TextView? = null
    var textViewDestination: TextView? = null
    var imageViewClient: ImageView? = null
    var imageViewPhone: ImageView?=null
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
            val intent = Intent(requireActivity(), ChatActivity::class.java)
            if(booking.id != null){
                intent.putExtra("idBooking",booking.id);
                Toast.makeText(context, booking.id, Toast.LENGTH_SHORT).show()

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
        clientProvider.getClient(booking?.idClient!!).addOnSuccessListener { document ->
            if(document.exists()){
                client = document.toObject(Client::class.java)
                textViewName?.text = client?.name

                if(client?.image != null){
                    if(client?.image != ""){
                        Glide.with(requireActivity()).load(client?.image).into(imageViewClient!!)
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