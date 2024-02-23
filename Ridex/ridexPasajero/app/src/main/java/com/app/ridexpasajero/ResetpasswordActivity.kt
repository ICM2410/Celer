package com.app.ridexpasajero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.ridexpasajero.databinding.ActivityResetpasswordBinding
import com.app.ridexpasajero.databinding.ActivityWelcomeBinding

class ResetpasswordActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityResetpasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetpasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            var intent = Intent(baseContext, SettingsMain::class.java)
            startActivity(intent)
        }
        binding.btnSaveContra.setOnClickListener{
            var intent = Intent(baseContext, SettingsMain::class.java)
            startActivity(intent)
        }

    }
}