package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityEarningdetailsBinding

class activity_earningdetails : AppCompatActivity() {
    private lateinit var binding: ActivityEarningdetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEarningdetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener {
            intent = Intent(this, activity_earnings::class.java)
            startActivity(intent)
        }
    }
}