package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityTakeLicensePhotoBackConfirmationBinding
import com.app.myapplication.databinding.ActivityTakeLicensePhotoFrontalConfirmationBinding
import com.app.myapplication.databinding.ActivityTakeLicensePhotoInfoBinding

class TakeLicensePhotoBackConfirmationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTakeLicensePhotoBackConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTakeLicensePhotoBackConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSaveLicenseBackPhoto.setOnClickListener {
            var intent = Intent(baseContext, TakeLicensePhotoFrontalConfirmationActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            var intent = Intent(baseContext, TakeLicensePhotoInfoActivity::class.java)
            startActivity(intent)
        }
    }
}