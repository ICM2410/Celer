package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityTakeLicensePhotoBackConfirmationBinding
import com.app.myapplication.databinding.ActivityTakeLicensePhotoFrontalConfirmationBinding
import com.app.myapplication.databinding.ActivityTakeLicensePhotoInfoBinding
import com.app.myapplication.databinding.ActivityThanksLicensePhotoBinding

class TakeLicensePhotoFrontalConfirmationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTakeLicensePhotoFrontalConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTakeLicensePhotoFrontalConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSaveLicenseFrontPhoto.setOnClickListener {
            var intent = Intent(baseContext, ThanksLicensePhotoActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            var intent = Intent(baseContext, TakeLicensePhotoBackConfirmationActivity::class.java)
            startActivity(intent)
        }
    }
}