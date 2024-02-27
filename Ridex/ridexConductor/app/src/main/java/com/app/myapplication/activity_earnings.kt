package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityEarningsBinding

class activity_earnings : AppCompatActivity() {
    private lateinit var binding: ActivityEarningsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEarningsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.detalles.setOnClickListener {
            intent = Intent(this, activity_cashout::class.java)
            startActivity(intent)
        }
        binding.cashout.setOnClickListener {
            intent = Intent(this, activity_earningdetails::class.java)
            startActivity(intent)
        }
    }
}