package com.app.ridexpasajero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.ridexpasajero.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGuardar.setOnClickListener{
            var intent = Intent(baseContext, HomeTransportActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener{
            var intent = Intent(baseContext, HomeTransportActivity::class.java)
            startActivity(intent)
        }

        binding.btnCancel.setOnClickListener{
            var intent = Intent(baseContext, HomeTransportActivity::class.java)
            startActivity(intent)
        }






    }
}