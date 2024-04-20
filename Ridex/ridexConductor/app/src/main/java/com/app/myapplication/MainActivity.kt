package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityMainBinding
import com.app.ridexpasajero.providers.AuthProvider

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val authProvider = AuthProvider();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIniciarSesion.setOnClickListener {
            var intent = Intent(baseContext, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.btnCrearCuenta.setOnClickListener {
            var intent = Intent(baseContext, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        if(authProvider.existSession()){
            var intent = Intent(baseContext, DisconnectedActivity::class.java)
            startActivity(intent)
        }
    }
}