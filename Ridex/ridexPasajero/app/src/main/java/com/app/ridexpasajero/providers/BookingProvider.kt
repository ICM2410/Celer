package com.app.ridexpasajero.providers

import android.util.Log
import com.app.ridexpasajero.models.Booking
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
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

    fun getBooking(): DocumentReference {
        return db.document(authProvider.getId())

    }

    fun remove(): Task<Void>{
        return db.document(authProvider.getId()).delete().addOnFailureListener{ exception ->
            Log.d("FIRESTORE", "Error: ${exception.message}")
        }
    }

}