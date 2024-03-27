package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityCashoutBinding

class CashoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCashoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCashoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener {
            val intent = Intent(baseContext, WalletActivity::class.java)
            startActivity(intent)
        }
        //if{
        binding.bAceptar.setOnClickListener {
            val intent = Intent(baseContext, CashoutSuccesfullActivity::class.java)
            startActivity(intent)
        }
        //}
        //else{
        binding.bAceptar.setOnClickListener {
            val intent = Intent(baseContext, CashoutFailActivity::class.java)
            startActivity(intent)
        }
        //}
        binding.btnBack.setOnClickListener {
            val intent = Intent(baseContext, EarningsActivity::class.java)
            startActivity(intent)
        }
    }
}