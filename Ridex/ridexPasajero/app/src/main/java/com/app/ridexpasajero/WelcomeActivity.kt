package com.app.ridexpasajero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.ridexpasajero.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCrearCuenta.setOnClickListener{
            var intent = Intent(baseContext, SignupActivity::class.java)
            startActivity(intent)
        }
        binding.btnIniciarSesion.setOnClickListener{
            var intent = Intent(baseContext, SigninActivity::class.java)
            startActivity(intent)
        }





    }
}