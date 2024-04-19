package com.app.ridexpasajero.providers

import com.app.ridexpasajero.models.Client
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class ClientProvider {

    val db = Firebase.firestore.collection("Clients")

    fun create(client:Client):Task<Void>{
        return db.document(client.id!!).set(client)
    }
}