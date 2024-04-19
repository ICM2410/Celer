package com.app.ridexpasajero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.ridexpasajero.databinding.ActivitySigninBinding
import com.app.ridexpasajero.providers.AuthProvider

class SigninActivity : AppCompatActivity() {
    private  lateinit var binding: ActivitySigninBinding
    val authProvider = AuthProvider();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            var intent = Intent(baseContext, WelcomeActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener{

            login();
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

    private fun login(){
        val email = binding.txtEmail.text.toString();
        val password = binding.txtPassword.text.toString();

        if(isValidForm(email, password)){
            authProvider.login(email, password).addOnCompleteListener{
                if(it.isSuccessful){
                    Toast.makeText(this@SigninActivity, "Formulario Valido", Toast.LENGTH_SHORT).show();
                    var intent = Intent(baseContext, HomeTransportActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this@SigninActivity, "Error Iniciando Sesion", Toast.LENGTH_SHORT).show()

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