package com.app.ridexpasajero

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import com.app.ridexpasajero.databinding.ActivityMainBinding
import com.app.ridexpasajero.providers.AuthProvider

class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    val authProvider = AuthProvider();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener{
            var intent = Intent(baseContext, InduccionDosActivity::class.java)
            startActivity(intent)
        }

        binding.btnSkiptoLocation.setOnClickListener{
            var intent = Intent(baseContext, AllowLocationActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onStart() {
        super.onStart()

        if(authProvider.existSession()){
            var intent = Intent(baseContext, HomeTransportActivity::class.java)
            startActivity(intent)
        }
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