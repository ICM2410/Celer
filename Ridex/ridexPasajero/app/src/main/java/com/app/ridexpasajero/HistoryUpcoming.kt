package com.app.ridexpasajero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.ridexpasajero.databinding.ActivityHistoryUpcomingBinding

class HistoryUpcoming : AppCompatActivity() {
    private  lateinit var binding: ActivityHistoryUpcomingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryUpcomingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCompleted.setOnClickListener{
            var intent = Intent(baseContext, HistoryCompleted::class.java)
            startActivity(intent)
        }
        binding.btnCancelled.setOnClickListener{
            var intent = Intent(baseContext, HistoryCancelled::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener{
            var intent = Intent(baseContext, HomeTransportActivity::class.java)
            startActivity(intent)
        }





    }
}