package com.app.ridexpasajero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.ridexpasajero.databinding.ActivityHomeTransportBinding
import com.app.ridexpasajero.databinding.ActivityTripBinding

class TripActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityTripBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomSheet = BottomSheetTrip();

        bottomSheet.show(supportFragmentManager, "BottomSheetrip")

        binding.btnRideBegin.setOnClickListener{
            var intent = Intent(baseContext, OnrideActivity::class.java)
            startActivity(intent)
        }

        binding.btnShowSlideUp.setOnClickListener{
            bottomSheet.show(supportFragmentManager, "BottomSheetrip")

        }


    }
}