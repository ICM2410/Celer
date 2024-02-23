package com.app.ridexpasajero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.ridexpasajero.databinding.ActivitySigninBinding
import com.app.ridexpasajero.databinding.ActivityWelcomeBinding

class SigninActivity : AppCompatActivity() {
    private  lateinit var binding: ActivitySigninBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            var intent = Intent(baseContext, WelcomeActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener{
            var intent = Intent(baseContext, HomeTransportActivity::class.java)
            startActivity(intent)
        }

        binding.btnLoginFace.setOnClickListener{
            var intent = Intent(baseContext, HomeTransportActivity::class.java)
            startActivity(intent)
        }

        binding.btnLoginGoogle.setOnClickListener{
            var intent = Intent(baseContext, HomeTransportActivity::class.java)
            startActivity(intent)
        }

        binding.idBtnLoginApple.setOnClickListener{
            var intent = Intent(baseContext, HomeTransportActivity::class.java)
            startActivity(intent)
        }

        binding.idBtnGotoCreateAccount.setOnClickListener{
            var intent = Intent(baseContext, SignupActivity::class.java)
            startActivity(intent)
        }




    }
}