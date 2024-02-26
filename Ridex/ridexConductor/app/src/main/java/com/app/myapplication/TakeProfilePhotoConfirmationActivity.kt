package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityRequiredStepsBinding
import com.app.myapplication.databinding.ActivityTakeProfilePhotoConfirmationBinding
import com.app.myapplication.databinding.ActivityTakeProfilePhotoInfoBinding
import com.app.myapplication.databinding.ActivityThanksProfilePhotoBinding

class TakeProfilePhotoConfirmationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTakeProfilePhotoConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTakeProfilePhotoConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            var intent = Intent(baseContext, TakeProfilePhotoInfoActivity::class.java)
            startActivity(intent)
        }

        binding.btnSaveProfilePhoto.setOnClickListener {
            var intent = Intent(baseContext, ThanksProfilePhotoActivity::class.java)
            startActivity(intent)
        }
    }
}