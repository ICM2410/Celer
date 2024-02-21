package com.app.ridexpasajero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.ridexpasajero.databinding.ActivityCompleteProfileBinding
import com.app.ridexpasajero.databinding.ActivityWelcomeBinding

class CompleteProfileActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityCompleteProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompleteProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            var intent = Intent(baseContext, SetPasswordActivity::class.java)
            startActivity(intent)
        }

        binding.btnCancel.setOnClickListener{
            var intent = Intent(baseContext, SetPasswordActivity::class.java)
            startActivity(intent)
        }

        binding.btnGuardar.setOnClickListener{
            var intent = Intent(baseContext, SigninActivity::class.java)
            startActivity(intent)
        }







    }
}