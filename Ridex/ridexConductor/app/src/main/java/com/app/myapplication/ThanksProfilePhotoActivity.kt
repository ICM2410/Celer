package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityTakeLicensePhotoInfoBinding
import com.app.myapplication.databinding.ActivityThanksProfilePhotoBinding

class ThanksProfilePhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThanksProfilePhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThanksProfilePhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGotoLicensePhotoInfo.setOnClickListener {
            var intent = Intent(baseContext, TakeLicensePhotoInfoActivity::class.java)
            startActivity(intent)
        }
    }
}