package com.app.myapplication.providers

import android.util.Log
import com.app.myapplication.models.Booking
import com.app.myapplication.models.Client
import com.app.ridexpasajero.providers.AuthProvider
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore

class BookingProvider {

    val db = Firebase.firestore.collection("Booking")

    val authProvider = AuthProvider();
    fun create(booking: Booking):Task<Void>{
        return db.document(authProvider.getId()).set(booking).addOnFailureListener{
            Log.d("FIRESTORE", "Error ${it.message}")
        }
    }

    fun getBooking(): Query{
        return db.whereEqualTo("idDriver", authProvider.getId())

    }

    fun updateStatus(idClient: String, status: String):Task<Void>{
        return db.document(idClient).update("status", status).addOnFailureListener{ exception ->
            Log.d("FIRESTORE", "ERROR ${exception.message}")
        }
    }

}