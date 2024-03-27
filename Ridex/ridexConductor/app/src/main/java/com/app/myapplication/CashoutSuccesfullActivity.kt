package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityCashoutsuccesfullBinding

class CashoutSuccesfullActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCashoutsuccesfullBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCashoutsuccesfullBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bRegresar.setOnClickListener {
            val intent = Intent(baseContext, CashoutActivity::class.java)
            startActivity(intent)
        }
    }
}