package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.myapplication.databinding.ActivityDisconnectedBinding
import com.app.myapplication.databinding.ActivityMainBinding
import com.app.myapplication.databinding.ActivitySignInBinding
import com.app.myapplication.databinding.ActivitySignUpBinding
import com.app.ridexpasajero.providers.AuthProvider

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    val authProvider = AuthProvider();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            var intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            login();
        }

        binding.BtnGotoCreateAccount.setOnClickListener{
            var intent = Intent(baseContext, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(){
        val email = binding.txtEmail.text.toString();
        val password = binding.txtPassword.text.toString();

        if(isValidForm(email, password)){
            authProvider.login(email, password).addOnCompleteListener{
                if(it.isSuccessful){
                    Toast.makeText(this@SignInActivity, "Formulario Valido", Toast.LENGTH_SHORT).show();
                    var intent = Intent(baseContext, DisconnectedActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this@SignInActivity, "Error Iniciando Sesion", Toast.LENGTH_SHORT).show()

                }
            }
        }


    }

    private fun isValidForm(email: String, password: String):Boolean{
        if(email.isEmpty()){

            Toast.makeText(this, "Ingresa tu Email", Toast.LENGTH_SHORT).show()
            return false;

        }

        if(password.isEmpty()){

            Toast.makeText(this, "Ingresa tu Password", Toast.LENGTH_SHORT).show()
            return false;

        }

        return true;

    }
}