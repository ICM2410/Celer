package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityDisconnectedBinding
import com.app.myapplication.databinding.ActivityMainBinding
import com.app.myapplication.databinding.ActivitySignInBinding
import com.app.myapplication.databinding.ActivitySignUpBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            var intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            var intent = Intent(baseContext, DisconnectedActivity::class.java)
            startActivity(intent)
        }

        binding.BtnGotoCreateAccount.setOnClickListener{
            var intent = Intent(baseContext, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}