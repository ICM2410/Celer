package com.app.ridexpasajero

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import com.app.ridexpasajero.databinding.ActivityPayRideBinding
import com.app.ridexpasajero.databinding.ActivityTripBinding

class PayRideActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityPayRideBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayRideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConfirmPay.setOnClickListener{
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
            showCustomCommentDialogBox()
        }

        dialog.show()
    }

    private fun showCustomCommentDialogBox() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog_coment)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnFeedback: Button = dialog.findViewById(R.id.btn_sendcomment)
        btnFeedback.setOnClickListener {
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

        val btnFeedback: Button = dialog.findViewById(R.id.btn_feedback)
        btnFeedback.setOnClickListener {
            dialog.dismiss()
                var intent = Intent(baseContext, HomeTransportActivity::class.java)
                startActivity(intent)

        }

        dialog.show()
    }
}