package com.app.ridexpasajero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.ridexpasajero.databinding.ActivityHistoryCancelledBinding
import com.app.ridexpasajero.databinding.ActivityHistoryUpcomingBinding

class HistoryCancelled : AppCompatActivity() {
    private  lateinit var binding: ActivityHistoryCancelledBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryCancelledBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCompleted.setOnClickListener{
            var intent = Intent(baseContext, HistoryCompleted::class.java)
            startActivity(intent)
        }
        binding.btnUpcoming.setOnClickListener{
            var intent = Intent(baseContext, HistoryUpcoming::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener{
            var intent = Intent(baseContext, HomeTransportActivity::class.java)
            startActivity(intent)
        }





    }
}