package com.app.myapplication

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.app.myapplication.databinding.ActivityPerfilBinding
import com.app.myapplication.models.Driver
import com.app.ridexpasajero.providers.AuthProvider
import com.app.ridexpasajero.providers.DriverProvider
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.File

class Perfil : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilBinding

    val driverProvider = DriverProvider();
    val authProvider = AuthProvider();

    var driverImageNull: String?=null


    private var imageFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDriver()

        binding.btnBack.setOnClickListener{
            val intent = Intent(this, DisconnectedActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.btnGuardar.setOnClickListener {
            updateInfo()
        }

        binding.circleImageProfile.setOnClickListener { checkPermissionAndSelectImage() }
    }
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            // Si se otorgan los permisos, selecciona la imagen.
            selectImage()
        } else {
            // Si no se otorgan los permisos, muestra un mensaje de advertencia.
            Toast.makeText(this, "Los permisos de la cámara y la galería son necesarios.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPermissionAndSelectImage() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Si los permisos ya están otorgados, selecciona la imagen.
            selectImage()
        } else {
            // Si los permisos no están otorgados, solicítalos.
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }


    private fun updateInfo(){
        val name = binding.textViewName.text.toString()
        val phone = binding.textViewNumber.text.toString()

        val driver = Driver(
            id = authProvider.getId(),
            name = name,
            phone = phone
        )

        if(imageFile != null){
            driverProvider.uploadImage(authProvider.getId(), imageFile!!).addOnSuccessListener { taskSnapshot ->
                driverProvider.getImageUrl().addOnSuccessListener { url ->
                    val imageUrl = url.toString()
                    driver.image = imageUrl
                    driverProvider.updateDriverInfo(driver).addOnCompleteListener {
                        if(it.isSuccessful){
                            Toast.makeText(this@Perfil, "Datos Actualizados Correctamente", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(this@Perfil, "No se pudo actualizar la informacion", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }

        }
        else{
            driver.image = driverImageNull

            driverProvider.updateDriverInfo(driver).addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(this@Perfil, "Datos Actualizados Correctamente", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this@Perfil, "No se pudo actualizar la informacion", Toast.LENGTH_SHORT).show()
                }
            }

        }




    }

    private fun getDriver(){
        driverProvider.getDriver(authProvider.getId()).addOnSuccessListener { document ->
            if(document.exists()){
                val driver = document.toObject(Driver::class.java)
                binding.textViewName.setText(driver?.name)
                binding.textViewNumber.setText(driver?.phone)

                if(driver?.image != null){
                    if(driver.image != ""){
                        Glide.with(this).load(driver.image).into(binding.circleImageProfile)
                        driverImageNull = driver.image

                    }
                }

            }
        }
    }

    private val startImageForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result: ActivityResult ->
        val resultCode = result.resultCode
        val data = result.data

        if(resultCode == Activity.RESULT_OK){
            val fileUri = data?.data
            imageFile = File(fileUri?.path)
            binding.circleImageProfile.setImageURI(fileUri)
        }
        else if(resultCode == ImagePicker.RESULT_ERROR){
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this, "Tarea Cancelada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectImage(){
        ImagePicker.with(this).crop().compress(1024).maxResultSize(1080,1080)
            .createIntent { intent ->
                startImageForResult.launch(intent)
            }
    }
}