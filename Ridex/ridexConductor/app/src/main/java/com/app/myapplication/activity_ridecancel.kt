package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityRidecancelBinding

class activity_ridecancel : AppCompatActivity() {
    private lateinit var binding: ActivityRidecancelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRidecancelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bAceptar.setOnClickListener {
            intent = Intent(this, activity_connected::class.java)
            startActivity(intent)
        }
    }
}