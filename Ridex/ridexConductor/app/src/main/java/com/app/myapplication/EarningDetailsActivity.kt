package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityEarningdetailsBinding

class EarningDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEarningdetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEarningdetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener {
            val intent = Intent(baseContext, EarningsActivity::class.java)
            startActivity(intent)
        }
    }
}