package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityCashoutBinding
import com.app.myapplication.databinding.ActivityWalletBinding

class activity_cashout : AppCompatActivity() {
    private lateinit var binding: ActivityCashoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCashoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener {
            intent = Intent(this, activity_wallet::class.java)
            startActivity(intent)
        }
        //if{
        binding.bAceptar.setOnClickListener {
            intent = Intent(this, activity_cashoutsuccesfull::class.java)
            startActivity(intent)
        }
        //}
        //else{
        binding.bAceptar.setOnClickListener {
            intent = Intent(this, activity_cashoutfail::class.java)
            startActivity(intent)
        }
        //}
    }
}