package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityMainBinding
import com.app.myapplication.databinding.ActivityPhoneVerificationBinding
import com.app.myapplication.databinding.ActivityRequiredStepsBinding
import com.app.myapplication.databinding.ActivitySignInBinding
import com.app.myapplication.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnBack.setOnClickListener{
            var intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignUp.setOnClickListener {
            var intent = Intent(baseContext, PhoneVerificationActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignUpGmail.setOnClickListener {
            var intent = Intent(baseContext, RequiredStepsActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignUpFace.setOnClickListener {
            var intent = Intent(baseContext, RequiredStepsActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignUpApple.setOnClickListener {
            var intent = Intent(baseContext, RequiredStepsActivity::class.java)
            startActivity(intent)
        }

        binding.btnGotoLogin.setOnClickListener {
            var intent = Intent(baseContext, RequiredStepsActivity::class.java)
            startActivity(intent)
        }

    }
}