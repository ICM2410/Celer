package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityWalletBinding

class activity_wallet : AppCompatActivity() {
    private lateinit var binding: ActivityWalletBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.verify.setOnClickListener{
            val intent = Intent(this, activity_cashout::class.java)
            startActivity(intent)
        }
        //binding.btnBack.setOnClickListener {

        //}
    }
}