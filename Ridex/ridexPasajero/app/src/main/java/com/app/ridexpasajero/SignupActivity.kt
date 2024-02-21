package com.app.ridexpasajero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.ridexpasajero.databinding.ActivitySignupBinding
import com.app.ridexpasajero.databinding.ActivityWelcomeBinding

class SignupActivity : AppCompatActivity() {
    private  lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener{
            var intent = Intent(baseContext, OtpVerificationActivity::class.java)
            startActivity(intent)
        }
        binding.btnSignUpGmail.setOnClickListener{
            var intent = Intent(baseContext, OtpVerificationActivity::class.java)
            startActivity(intent)
        }
        binding.btnSignUpFace.setOnClickListener{
            var intent = Intent(baseContext, OtpVerificationActivity::class.java)
            startActivity(intent)
        }
        binding.btnSignUpApple.setOnClickListener{
            var intent = Intent(baseContext, OtpVerificationActivity::class.java)
            startActivity(intent)
        }

        binding.btnGotoLogin.setOnClickListener{
            var intent = Intent(baseContext, SigninActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener{
            var intent = Intent(baseContext, WelcomeActivity::class.java)
            startActivity(intent)
        }






    }
}