package com.app.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.myapplication.databinding.ActivityConnectedBinding

class ConnectedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConnectedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConnectedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomsheet = BottomsSheetRideActivity()

        binding.slideup.setOnClickListener {
            bottomsheet.show(supportFragmentManager, "BottomSheeOffert")
        }
    }
}