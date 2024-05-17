package com.app.ridexpasajero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.ridexpasajero.databinding.ActivityChat2Binding
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.app.ridexpasajero.models.Messages
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
class ChatActivity2 : AppCompatActivity() {
    private  lateinit var binding: ActivityChat2Binding
    private lateinit var carrera: String
    private lateinit var db: FirebaseFirestore
    private lateinit var ref: DocumentReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChat2Binding.inflate(layoutInflater)

        carrera = intent.getStringExtra("idBooking").toString()
        Toast.makeText(this, carrera, Toast.LENGTH_SHORT).show()
        setContentView(binding.root)



        db = FirebaseFirestore.getInstance()
        ref = db.collection("Messages").document(carrera)
        binding.btnBack.setOnClickListener {}
        binding.enviar.setOnClickListener {
            val newMessage = binding.editTextText.text.toString()
            if (newMessage.isNotEmpty()) {
                addMessageToFirestore(newMessage)
            } else {
                Toast.makeText(this, "El mensaje no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }
        if (carrera.isNotEmpty()) {
            ref.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("TAG", "onCreate: $error", )
                    Toast.makeText(baseContext, "Ha ocurrido un error para obtener la info del chat", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    val data = snapshot.data
                    val message = createMessageFromSnapshot(data)
                    if (message != null) {
                        actualizarUI(message)
                    }
                } else {
                    //Toast.makeText(baseContext, "No existe el documento", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            //Toast.makeText(baseContext, "Ha ocurrido un error al obtener el id del chat", Toast.LENGTH_SHORT).show()
        }
    }

    fun createMessageFromSnapshot(data: Map<String, Any?>?): Messages? {
        if (data == null) return null

        val id = data["id"] as? String
        val owner = (data["owner"] as? List<Boolean>)?.toTypedArray() ?: emptyArray()
        val chat = (data["chat"] as? List<String>)?.toTypedArray() ?: emptyArray()

        return Messages(id, owner, chat)
    }
    private fun actualizarUI(message: Messages) {
        binding.messagesContainer.removeAllViews()
        for (i in message.chat.indices) {
            val textView = TextView(this)
            textView.text = message.chat[i]
            textView.textSize = 16f
            textView.setPadding(8, 8, 8, 8)

            if (message.owner[i]) {
                textView.setTextColor(ContextCompat.getColor(this, R.color.gray))
                textView.setPadding(10, 10, 10, 10 );
                textView.gravity = Gravity.END;            }
            else {
                textView.setTextColor(ContextCompat.getColor(this, R.color.black))
                textView.setPadding(10, 10, 10, 10 );
                textView.gravity = Gravity.START;
            }

            binding.messagesContainer.addView(textView)
        }
    }
    private fun addMessageToFirestore(newMessage: String) {
        ref.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val currentMessages = createMessageFromSnapshot(documentSnapshot.data)
                if (currentMessages != null) {
                    val updatedOwner = currentMessages.owner.toMutableList()
                    val updatedChat = currentMessages.chat.toMutableList()

                    updatedOwner.add(true)
                    updatedChat.add(newMessage)

                    ref.update(mapOf(
                        "owner" to updatedOwner,
                        "chat" to updatedChat
                    )).addOnSuccessListener {
                        //Toast.makeText(this, "Mensaje enviado", Toast.LENGTH_SHORT).show()
                        binding.editTextText.text.clear()
                    }.addOnFailureListener {
                       // Toast.makeText(this, "Error al enviar el mensaje", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                val initialOwner = mutableListOf(true)
                val initialChat = mutableListOf(newMessage)

                val initialData = mapOf(
                    "id" to carrera,
                    "owner" to initialOwner,
                    "chat" to initialChat
                )

                ref.set(initialData)
                    .addOnSuccessListener {
                        //Toast.makeText(this, "Documento creado y mensaje enviado", Toast.LENGTH_SHORT).show()
                        binding.editTextText.text.clear()
                    }.addOnFailureListener {
                        //Toast.makeText(this, "Error al crear el documento", Toast.LENGTH_SHORT).show()
                    }
            }
        }.addOnFailureListener {
            //Toast.makeText(this, "Error al obtener el documento", Toast.LENGTH_SHORT).show()
        }
    }
}