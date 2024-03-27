package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityThanksLicensePhotoBinding

class ThanksLicensePhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThanksLicensePhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThanksLicensePhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGotoLicensePhotoInfo.setOnClickListener {
            var intent = Intent(baseContext, DisconnectedActivity::class.java)
            startActivity(intent)
        }
    }
}