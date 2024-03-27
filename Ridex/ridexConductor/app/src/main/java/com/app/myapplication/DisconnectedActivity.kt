package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityDisconnectedBinding

class DisconnectedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDisconnectedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisconnectedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.Desconectado.setOnClickListener {
            val intent = Intent(baseContext,ConnectedActivity::class.java)
            startActivity(intent)
        }
        binding.btnBack.setOnClickListener {
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
        }
        binding.perfil.setOnClickListener {
            val intent = Intent(baseContext, WalletActivity::class.java)
            startActivity(intent)
        }
    }
}