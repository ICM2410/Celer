package com.app.ridexpasajero

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetOntrip :  BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_onride, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setOnClickListener{
            dismiss()
        }

        val btnPay: Button = view.findViewById(R.id.btnGoPay)


        btnPay.setOnClickListener{
            dismiss()
            var intent = Intent(activity, PayRideActivity::class.java)
            startActivity(intent)

        }









    }
}