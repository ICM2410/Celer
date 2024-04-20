package com.app.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.myapplication.databinding.ActivityMainBinding
import com.app.myapplication.databinding.ActivityPhoneVerificationBinding
import com.app.myapplication.databinding.ActivityRequiredStepsBinding
import com.app.myapplication.databinding.ActivitySignInBinding
import com.app.myapplication.databinding.ActivitySignUpBinding
import com.app.myapplication.models.Driver
import com.app.ridexpasajero.providers.AuthProvider
import com.app.ridexpasajero.providers.ClientProvider
import com.app.ridexpasajero.providers.DriverProvider

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private  val authProvider = AuthProvider();
    private val driverProvider = DriverProvider();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnBack.setOnClickListener{
            var intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignUp.setOnClickListener {
            register()
        }

        binding.btnGotoLogin.setOnClickListener {
            var intent = Intent(baseContext, RequiredStepsActivity::class.java)
            startActivity(intent)
        }

    }

    private fun register(){
        val name = binding.txtNombre.text.toString();
        val phone = binding.txtNumero.text.toString();
        val email = binding.txtEmail.text.toString();
        val password = binding.txtPassword.text.toString();

        if(isValidForm(email, password, name, phone)){
            Toast.makeText(this, "Formulario Valido", Toast.LENGTH_SHORT).show()
            authProvider.register(email, password).addOnCompleteListener{
                if(it.isSuccessful){
                    val driver = Driver(id = authProvider.getId(), name = name, phone=phone, email = email);
                    driverProvider.create(driver).addOnCompleteListener{
                        if(it.isSuccessful){
                            Toast.makeText(this@SignUpActivity, "Registro Existoso", Toast.LENGTH_LONG).show()
                            var intent = Intent(baseContext, SignInActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this@SignUpActivity, "Error almacenando datos del usuario", Toast.LENGTH_LONG).show()

                        }
                    }
                }
                else{
                    Toast.makeText(this@SignUpActivity, "Registro Fallido ${it.exception.toString()}", Toast.LENGTH_LONG).show()
                }
            }
        }


    }

    private fun isValidForm(email: String, password: String, name: String, phone: String):Boolean{
        if(email.isEmpty()){
            Toast.makeText(this, "Ingresa tu Email", Toast.LENGTH_SHORT).show()
            return false;

        }

        if(password.isEmpty()){
            Toast.makeText(this, "Ingresa tu Password", Toast.LENGTH_SHORT).show()
            return false;

        }
        if(password.length < 6){
            Toast.makeText(this, "Tu Password debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
            return false;

        }
        if(name.isEmpty()){
            Toast.makeText(this, "Ingresa tu Nombre", Toast.LENGTH_SHORT).show()
            return false;


        }
        if(phone.isEmpty()){
            Toast.makeText(this, "Ingresa tu Numero", Toast.LENGTH_SHORT).show()
            return false;

        }

        return true;

    }
}