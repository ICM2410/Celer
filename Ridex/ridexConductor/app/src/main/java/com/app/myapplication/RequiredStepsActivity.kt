package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityRequiredStepsBinding
import com.app.myapplication.databinding.ActivitySetPasswordBinding
import com.app.myapplication.databinding.ActivityTakeLicensePhotoInfoBinding
import com.app.myapplication.databinding.ActivityTakeProfilePhotoInfoBinding

class RequiredStepsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRequiredStepsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequiredStepsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGotoProfilePhotoInfo.setOnClickListener {
            var intent = Intent(baseContext, TakeProfilePhotoInfoActivity::class.java)
            startActivity(intent)
        }

        binding.btnGotoLicenseInfo.setOnClickListener {
            var intent = Intent(baseContext, TakeLicensePhotoInfoActivity::class.java)
            startActivity(intent)
        }

        binding.btnNextGotoProfilePhotoInfo.setOnClickListener {
            var intent = Intent(baseContext, TakeProfilePhotoInfoActivity::class.java)
            startActivity(intent)
        }





    }
}