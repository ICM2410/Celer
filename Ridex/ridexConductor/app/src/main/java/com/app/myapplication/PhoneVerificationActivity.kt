package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityPhoneVerificationBinding
import com.app.myapplication.databinding.ActivitySetPasswordBinding
import com.app.myapplication.databinding.ActivitySignUpBinding

class PhoneVerificationActivity : AppCompatActivity() {

    private lateinit var binding:ActivityPhoneVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            var intent  = Intent(baseContext, SignUpActivity::class.java)
        }

        binding.BtnPhoneVerification.setOnClickListener {
            var intent = Intent(baseContext, SetPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}