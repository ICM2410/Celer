package com.app.ridexpasajero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.ridexpasajero.databinding.ActivityInduccionTresBinding

class InduccionTresActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityInduccionTresBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInduccionTresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener{
            var intent = Intent(baseContext, AllowLocationActivity::class.java)
            startActivity(intent)
        }

        binding.btnSkiptoLocation.setOnClickListener{
            var intent = Intent(baseContext, AllowLocationActivity::class.java)
            startActivity(intent)
        }


    }
}