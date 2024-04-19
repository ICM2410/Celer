package com.app.ridexpasajero

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import com.app.ridexpasajero.databinding.ActivityAllowLocationBinding

class AllowLocationActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityAllowLocationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllowLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showCustomDialogBox();


    }

    private fun showCustomDialogBox() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog_location)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnFeedback: Button = dialog.findViewById(R.id.btnAllowLocation)
        btnFeedback.setOnClickListener {
            dialog.dismiss()

                var intent = Intent(baseContext, WelcomeActivity::class.java)
                startActivity(intent)

        }

        val btnSkipLocation: Button = dialog.findViewById(R.id.btnSkipLocation)
        btnSkipLocation.setOnClickListener {
            dialog.dismiss()

            var intent = Intent(baseContext, WelcomeActivity::class.java)
            startActivity(intent)

        }

        dialog.show()
    }
}