package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityPhoneVerificationBinding
import com.app.myapplication.databinding.ActivityRequiredStepsBinding
import com.app.myapplication.databinding.ActivitySetPasswordBinding

class SetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            var intent = Intent(baseContext, PhoneVerificationActivity::class.java)
            startActivity(intent)
        }

        binding.btnGotoRequiredSteps.setOnClickListener {
            var intent = Intent(baseContext, RequiredStepsActivity::class.java)
            startActivity(intent)
        }
    }
}