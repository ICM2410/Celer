package com.app.ridexpasajero

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_confirm_ride)

        val btnPago : Button  = findViewById(R.id.btnConfirmPay);

        btnPago.setOnClickListener{
            showCustomDialogBox()

        }



    }

    private fun showCustomDialogBox() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog_confirmpayment)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnFeedback: Button = dialog.findViewById(R.id.btn_feedback)
        btnFeedback.setOnClickListener {
            dialog.dismiss()
            showCustomDialogFeedback()
        }

        dialog.show()
    }

    private fun showCustomDialogFeedback() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog_coment)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnSendFeedback: Button = dialog.findViewById(R.id.btn_sendcomment)
        btnSendFeedback.setOnClickListener {
            dialog.dismiss()
            showCustomDialogThanksFeedback()
        }

        dialog.show()
    }

    private fun showCustomDialogThanksFeedback() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog_thankspay)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()
    }

}