package com.app.ridexpasajero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.ridexpasajero.databinding.ActivityCarDetailsBinding

class CarDetailsActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityCarDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGoHome.setOnClickListener{
            var intent = Intent(baseContext, HomeTransportActivity::class.java)
            startActivity(intent)
        }


        binding.btnConfirmRide.setOnClickListener{
            var intent = Intent(baseContext, ThanksConfirmrideActivity::class.java)
            startActivity(intent)
        }



    }


}