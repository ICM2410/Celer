package com.app.ridexpasajero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.ridexpasajero.databinding.ActivityInduccionDosBinding

class InduccionDosActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityInduccionDosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInduccionDosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener{
            var intent = Intent(baseContext, InduccionTresActivity::class.java)
            startActivity(intent)
        }
        binding.btnSkiptoLocation.setOnClickListener{
            var intent = Intent(baseContext, AllowLocationActivity::class.java)
            startActivity(intent)
        }



    }
}