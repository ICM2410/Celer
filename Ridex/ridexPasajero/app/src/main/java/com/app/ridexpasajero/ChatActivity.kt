package com.app.ridexpasajero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.ridexpasajero.databinding.ActivityChatBinding
import com.app.ridexpasajero.databinding.ActivityWelcomeBinding

class ChatActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            var intent = Intent(baseContext, TripActivity::class.java)
            startActivity(intent)
        }








    }
}