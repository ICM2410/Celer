package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityTakeLicensePhotoBackConfirmationBinding
import com.app.myapplication.databinding.ActivityTakeLicensePhotoInfoBinding

class TakeLicensePhotoInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTakeLicensePhotoInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTakeLicensePhotoInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGotoTakeLicensePhotoBack.setOnClickListener {
            var intent = Intent(baseContext, TakeLicensePhotoBackConfirmationActivity::class.java)
            startActivity(intent)
        }
    }
}