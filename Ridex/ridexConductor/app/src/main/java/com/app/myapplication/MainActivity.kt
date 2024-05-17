package com.app.myapplication

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.app.myapplication.databinding.ActivityMainBinding
import com.app.ridexpasajero.providers.AuthProvider
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val authProvider = AuthProvider();
    var notid = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIniciarSesion.setOnClickListener {
            var intent = Intent(baseContext, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.btnCrearCuenta.setOnClickListener {
            var intent = Intent(baseContext, SignUpActivity::class.java)
            startActivity(intent)
        }

        observarCambiosDisponibilidad()
    }

    override fun onStart() {
        super.onStart()

        if(authProvider.existSession()){
            var intent = Intent(baseContext, DisconnectedActivity::class.java)
            startActivity(intent)
        }
    }


    fun observarCambiosDisponibilidad() {
        val usuariosRef = FirebaseFirestore.getInstance().collection("Booking")
        usuariosRef.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Log.e("ERROR", "Error al obtener la lista de bookings: ", exception)
                return@addSnapshotListener
            }

            // Iterar sobre los cambios en el snapshot
            for (change in snapshot!!.documentChanges) {
                if (change.type == DocumentChange.Type.ADDED) {
                    // Se ha agregado un nuevo documento
                    val document = change.document
                    val usuarioId = document.id
                    createNotificationChannel()
                    Log.d("NUEVO REGISTRO", "Se ha agregado una nueva instancia de Booking con ID: $usuarioId")
                    notify(buildNotification(
                        "Hay una nuevo viaje!",
                        "Alguien necesita que lo lleves, quieres aceptar el viaje?",
                        R.drawable.logo2,
                        DisconnectedActivity::class.java,
                        usuarioId
                    ))
                }
            }
        }

    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel";
            val description = "channel description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("Test", name, importance)
            channel.setDescription(description)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel (channel)
        }
    }

    private fun buildNotification(title: String, message: String, icon: Int, target: Class<*>, id: String) : Notification {
        val builder = NotificationCompat.Builder(this, "Test")
        builder.setSmallIcon(icon)
        builder.setContentTitle(title);
        builder.setContentText(message);builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        val intent = Intent(this, target)
        intent.putExtra("rastreadoId", id)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        builder.setContentIntent(pendingIntent)
        builder.setAutoCancel(true) //Remueve la noAﬁcación cuando se toque
        return builder.build()
    }

    fun notify(notification: Notification) {
        notid++
        val notificationManager = NotificationManagerCompat.from(this)
        if(checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(notid, notification)
        }

    }
}