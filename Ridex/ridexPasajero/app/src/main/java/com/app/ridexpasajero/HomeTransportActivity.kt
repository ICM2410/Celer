package com.app.ridexpasajero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.ridexpasajero.databinding.ActivityHomeTransportBinding

class HomeTransportActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityHomeTransportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeTransportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomSheet = BottomSheetFragmentLocation();

        binding.btnShowSlideUp.setOnClickListener{
            bottomSheet.show(supportFragmentManager, "BottomSheetSelectRide")

        }

        binding.btnWallet.setOnClickListener{
            var intent = Intent(baseContext, WalletActivity::class.java)
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






    }
}