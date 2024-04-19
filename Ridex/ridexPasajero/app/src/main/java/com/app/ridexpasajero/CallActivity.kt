package com.app.ridexpasajero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.ridexpasajero.databinding.ActivityCallBinding

class CallActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityCallBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            var intent = Intent(baseContext, TripActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack2.setOnClickListener{
            var intent = Intent(baseContext, TripActivity::class.java)
            startActivity(intent)
        }

    }
}