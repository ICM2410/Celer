package com.app.ridexpasajero

import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.app.ridexpasajero.databinding.ActivityResetpasswordBinding

class ResetpasswordActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityResetpasswordBinding


    private var cancellationSignal: CancellationSignal? = null
    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get () =
        @RequiresApi(Build.VERSION_CODES.P)
        object: BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)
                notifyUser("Authentication error: $errString")
            }
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    notifyUser("Todo salio bien! Tu contraseña ha sido cambiada")
                    startActivity(Intent(this@ResetpasswordActivity, SettingsMain::class.java))
                }
            }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {

        checkBiometricSupport()

        super.onCreate(savedInstanceState)
        binding = ActivityResetpasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            var intent = Intent(baseContext, SettingsMain::class.java)
            startActivity(intent)
        }
        binding.btnSaveContra.setOnClickListener{
            val biometricPrompt  = BiometricPrompt.Builder(this)
                .setTitle("Confirmar Cambios")
                .setSubtitle("Pon tu huella en el lector para confirmar los cambios")
                .setDescription("No dudamos que seas tu, pero una última verificación no sobra")
                .setNegativeButton( "Cancelar", this.mainExecutor, DialogInterface.OnClickListener { dialog, which ->
                    notifyUser ("Autenticación Cancelada")
        }).build()
        biometricPrompt.authenticate(getCancellationSignal(), mainExecutor, authenticationCallback)
        }

    }

    private fun notifyUser(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    private fun getCancellationSignal() : CancellationSignal {
        cancellationSignal = CancellationSignal ()
        cancellationSignal?. setOnCancelListener {
            notifyUser ("Autenticación Cancelada")
        }
            return cancellationSignal as CancellationSignal
    }

    private fun checkBiometricSupport () : Boolean {
        val keyguardManager: KeyguardManager =
            getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        if (!keyguardManager.isKeyguardSecure) {
            notifyUser("No hay ninguna huella registrada hasta el momento")
            return false
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.USE_BIOMETRIC
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            notifyUser("Permiso de huella no habilitado")
            return false
        }
        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            true
        } else true
    }
}