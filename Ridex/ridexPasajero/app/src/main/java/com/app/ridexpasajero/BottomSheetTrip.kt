package com.app.ridexpasajero

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetTrip :  BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_coming, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setOnClickListener{
            dismiss()
        }
        val btnChat: Button = view.findViewById(R.id.btnChat)
        val btnCall: Button = view.findViewById(R.id.btn_Call)
        val btnCancel: Button = view.findViewById(R.id.btn_Cancell)


        btnChat.setOnClickListener{
            dismiss()
            var intent = Intent(activity, ChatActivity::class.java)
            startActivity(intent)

        }
        btnCall.setOnClickListener{
            dismiss()
            var intent = Intent(activity, CallActivity::class.java)
            startActivity(intent)

        }

        btnCancel.setOnClickListener{
            dismiss()
            var intent = Intent(activity, HomeTransportActivity::class.java)
            startActivity(intent)

        }









    }
}