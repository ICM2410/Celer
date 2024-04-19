package com.app.ridexpasajero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.ridexpasajero.databinding.ActivitySettingsMainBinding

class SettingsMain : AppCompatActivity() {
    private  lateinit var binding: ActivitySettingsMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            var intent = Intent(baseContext, HomeTransportActivity::class.java)
            startActivity(intent)
        }
        binding.btnCambiarContra.setOnClickListener{
            var intent = Intent(baseContext, ResetpasswordActivity::class.java)
            startActivity(intent)
        }
        binding.btnPoliticaPrivacidad.setOnClickListener{
            var intent = Intent(baseContext, PrivacyPolicyActivity::class.java)
            startActivity(intent)
        }

        binding.btnContactanos.setOnClickListener{
            var intent = Intent(baseContext, ContactusActivity::class.java)
            startActivity(intent)
        }

        binding.btnDeleteCuenta.setOnClickListener{
            var intent = Intent(baseContext, DeleteaccountActivity::class.java)
            startActivity(intent)
        }






    }
}