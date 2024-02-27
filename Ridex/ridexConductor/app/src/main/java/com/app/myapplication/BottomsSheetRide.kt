package com.app.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.myapplication.databinding.ActivityConnectedBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.content.Intent
import android.widget.Button

class BottomsSheetRide : BottomSheetDialogFragment(){
    private lateinit var binding: ActivityConnectedBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_bottom_sheet_offert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnClickListener{
            dismiss()
        }
        val btncancel : Button = view.findViewById(R.id.cancel)
        val btnaccept : Button = view.findViewById(R.id.aceptar)

        btncancel.setOnClickListener {
            dismiss()
            val intent = Intent(activity, activity_ridecancel::class.java)
            startActivity(intent)
        }
        btnaccept.setOnClickListener {
            dismiss()
            val intent = Intent(activity, activity_earnings::class.java)
            startActivity(intent)
        }
    }
}
