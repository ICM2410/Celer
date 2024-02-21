package com.app.ridexpasajero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.ridexpasajero.databinding.ActivityOtpVerificationBinding
import com.app.ridexpasajero.databinding.ActivityWelcomeBinding

class OtpVerificationActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityOtpVerificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            var intent = Intent(baseContext, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.btnSendAgain.setOnClickListener{
            Toast.makeText(this, "OTP enviado nuevamente", Toast.LENGTH_SHORT).show();
        }

        binding.btnVerify.setOnClickListener{
            var intent = Intent(baseContext, SetPasswordActivity::class.java)
            startActivity(intent)
        }






    }
}