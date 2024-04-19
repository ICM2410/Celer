package com.app.ridexpasajero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.ridexpasajero.databinding.ActivityHistoryCompletedBinding

class HistoryCompleted : AppCompatActivity() {
    private  lateinit var binding: ActivityHistoryCompletedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryCompletedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUpcoming.setOnClickListener{
            var intent = Intent(baseContext, HistoryUpcoming::class.java)
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