package com.app.myapplication

import android.content.Intent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.app.myapplication.models.Booking
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetOffertActivity : BottomSheetDialogFragment() {

    private lateinit var textViewOrigin: TextView
    private lateinit var textViewDestination: TextView
    private lateinit var textViewTimeDistance: TextView
    private lateinit var btnAccept: Button
    private lateinit var btnRechazar: Button


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
        val booking = Booking.fromJson(data!!)

        textViewOrigin.text = booking?.origin
        textViewDestination.text = booking?.destination
        textViewTimeDistance.text = "${booking?.time} min - ${booking?.km} km"


        return view
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
}