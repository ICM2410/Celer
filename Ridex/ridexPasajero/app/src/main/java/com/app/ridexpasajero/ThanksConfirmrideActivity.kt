package com.app.ridexpasajero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.ridexpasajero.databinding.ActivityThanksConfirmrideBinding

class ThanksConfirmrideActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityThanksConfirmrideBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThanksConfirmrideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConfirm.setOnClickListener{
            var intent = Intent(baseContext, TripActivity::class.java)
            startActivity(intent)
        }






    }
}