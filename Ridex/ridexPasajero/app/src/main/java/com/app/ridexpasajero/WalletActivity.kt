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



        binding.btnHistorial.setOnClickListener{
            var intent = Intent(baseContext, HistoryUpcoming::class.java)
            startActivity(intent)
        }

        binding.btnProfile.setOnClickListener{
            var intent = Intent(baseContext, ProfileActivity::class.java)
            startActivity(intent)

        }

        binding.btnConfig.setOnClickListener{
            var intent = Intent(baseContext, SettingsMain::class.java)
            startActivity(intent)

        }

        binding.btnHome.setOnClickListener{
            var intent = Intent(baseContext, HomeTransportActivity::class.java)
            startActivity(intent)

        }





    }
}