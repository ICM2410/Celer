package com.app.myapplication

import android.content.Intent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetOffertActivity : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_offert2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setOnClickListener{
            dismiss()
        }
        val aceptar: Button = view.findViewById(R.id.aceptar)
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
        }
    }
}