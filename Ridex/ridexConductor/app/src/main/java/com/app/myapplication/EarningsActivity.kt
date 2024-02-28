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
            intent = Intent(this, CashoutActivity::class.java)
            startActivity(intent)
        }
        binding.cashout.setOnClickListener {
            intent = Intent(this, EarningDetailsActivity::class.java)
            startActivity(intent)
        }
    }
}