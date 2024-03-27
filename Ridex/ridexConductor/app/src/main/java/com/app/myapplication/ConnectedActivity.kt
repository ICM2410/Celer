package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityConnectedBinding

class ConnectedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConnectedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConnectedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.slideup.setOnClickListener {
            val bottomsheet = BottomSheetOffertActivity()
            bottomsheet.show(supportFragmentManager, "BottomSheetOffert2")
        }
        binding.button2.setOnClickListener {
            val intent = Intent(baseContext, DisconnectedActivity:: class.java)
            startActivity(intent)
        }
        binding.btnBack.setOnClickListener {
            val intent = Intent(baseContext,DisconnectedActivity::class.java)
            startActivity(intent)
        }
        binding.perfil.setOnClickListener {
            val intent = Intent(baseContext, EarningsActivity::class.java)
            startActivity(intent)
        }
    }
}