package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityEarningsBinding

class EarningsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEarningsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEarningsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.detalles.setOnClickListener {
            val intent = Intent(baseContext, CashoutActivity::class.java)
            startActivity(intent)
        }
        binding.cashout.setOnClickListener {
            val intent = Intent(baseContext, EarningDetailsActivity::class.java)
            startActivity(intent)
        }
        binding.btnBack.setOnClickListener {
            val intent = Intent(baseContext, ConnectedActivity::class.java)
            startActivity(intent)
        }
    }
}