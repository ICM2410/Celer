package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityCashoutBinding
import com.app.myapplication.databinding.ActivityCashoutfailBinding

class activity_cashoutfail : AppCompatActivity() {
    private lateinit var binding: ActivityCashoutfailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCashoutfailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bRegresar.setOnClickListener {
            intent = Intent(this, activity_cashout::class.java)
            startActivity(intent)
        }
    }
}