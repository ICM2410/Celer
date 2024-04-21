package com.app.ridexpasajero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.ridexpasajero.databinding.ActivityOnrideBinding

class OnrideActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityOnrideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnrideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomSheet = BottomSheetOntrip();

        bottomSheet.show(supportFragmentManager, "BottomSheeOntrip")




    }
}