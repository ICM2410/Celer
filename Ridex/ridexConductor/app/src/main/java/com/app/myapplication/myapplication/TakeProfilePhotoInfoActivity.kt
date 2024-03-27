package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityRequiredStepsBinding
import com.app.myapplication.databinding.ActivityTakeProfilePhotoConfirmationBinding
import com.app.myapplication.databinding.ActivityTakeProfilePhotoInfoBinding

class TakeProfilePhotoInfoActivity : AppCompatActivity() {

    private lateinit var binding:ActivityTakeProfilePhotoInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTakeProfilePhotoInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            var intent = Intent(baseContext, RequiredStepsActivity::class.java)
            startActivity(intent)
        }

        binding.btnGotoTakeProfilePhoto.setOnClickListener {
            var intent = Intent(baseContext, TakeProfilePhotoConfirmationActivity::class.java)
            startActivity(intent)
        }
    }
}