package com.app.ridexpasajero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.ridexpasajero.databinding.ActivityWalletBinding
import com.app.ridexpasajero.databinding.ActivityWelcomeBinding

class WalletActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityWalletBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddWallet.setOnClickListener{
            var intent = Intent(baseContext, PaymethodActivity::class.java)
            startActivity(intent)
        }





    }
}