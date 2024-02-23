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
import com.app.ridexpasajero.databinding.ActivityPaymethodBinding

class PaymethodActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityPaymethodBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymethodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddMethod.setOnClickListener{
            showCustomDialogBox()
        }






    }

    private fun showCustomDialogBox() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog_methodadded)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnGoWallet: Button = dialog.findViewById(R.id.btnGoToWallet)
        btnGoWallet.setOnClickListener {
            dialog.dismiss()
            var intent = Intent(baseContext, WalletActivity::class.java)
            startActivity(intent)
        }

        dialog.show()
    }
}