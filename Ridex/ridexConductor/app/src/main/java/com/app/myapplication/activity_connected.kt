package com.app.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityConnectedBinding

class activity_connected : AppCompatActivity() {
    private lateinit var binding: ActivityConnectedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConnectedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomsheet = BottomsSheetRide()

        binding.slideup.setOnClickListener {
            bottomsheet.show(supportFragmentManager, "BottomSheeOffert")
        }
    }
}