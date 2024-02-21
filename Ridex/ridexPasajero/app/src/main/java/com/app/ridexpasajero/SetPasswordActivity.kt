package com.app.ridexpasajero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.ridexpasajero.databinding.ActivitySetPasswordBinding
import com.app.ridexpasajero.databinding.ActivityWelcomeBinding

class SetPasswordActivity : AppCompatActivity() {
    private  lateinit var binding: ActivitySetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            var intent = Intent(baseContext, SignupActivity::class.java)
            startActivity(intent)
        }
        binding.btnGotoProfile.setOnClickListener{
            var intent = Intent(baseContext, CompleteProfileActivity::class.java)
            startActivity(intent)
        }









    }
}