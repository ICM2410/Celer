package com.app.ridexpasajero.providers

import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.app.myapplication.models.Driver
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import java.io.File
import java.util.HashMap

class DriverProvider {

    val db = Firebase.firestore.collection("Drivers")
    var storage = FirebaseStorage.getInstance().getReference().child("profile")
    var storage2 = FirebaseStorage.getInstance().getReference().child("carpics")

    fun create(driver:Driver):Task<Void>{
        return db.document(driver.id!!).set(driver)
    }

    fun uploadImage(id:String, file: File): StorageTask<UploadTask.TaskSnapshot>{
        var fromfile = Uri.fromFile(file)

        val ref = storage.child("${id}.jpg")
        storage = ref
        val uploadTask = ref.putFile(fromfile)

        return uploadTask.addOnFailureListener {
            Log.d("STORAGE", "ERROR")
        }
    }

    fun uploadImageCar(id:String, file: File): StorageTask<UploadTask.TaskSnapshot>{
        var fromfile = Uri.fromFile(file)

        val ref = storage2.child("${id}.jpg")
        storage2 = ref
        val uploadTask = ref.putFile(fromfile)

        return uploadTask.addOnFailureListener {
            Log.d("STORAGE", "ERROR")
        }
    }

    fun getImageUrl():Task<Uri>{
        return storage.downloadUrl
    }

    fun getCarImageUrl():Task<Uri>{
        return storage2.downloadUrl
    }

    fun getDriver(idDriver:String): Task<DocumentSnapshot>{
        return db.document(idDriver).get()
    }

    fun updateDriverInfo(driver: Driver):Task<Void>{
        val map: MutableMap<String, Any> = HashMap()
        map["name"] = driver?.name!!
        map["phone"] = driver?.phone!!
        map["image"] = driver?.image!!
        /*map["color"] = driver?.color!!
        map["color"] = driver?.color!!
        map["color"] = driver?.color!! map["brandcar"] = driver?.brandcar!!
        map["capacidad"] = driver?.capacidad!!
        map["color"] = driver?.color!!
        map["combustible"] = driver?.combustible!!
        */

        return db.document(driver?.id!!).update(map)
    }

    fun updateCarInfo(driver: Driver):Task<Void>{
        val map: MutableMap<String, Any> = HashMap()
        map["modelo"] = driver?.modelo!!
        map["color"] = driver?.color!!
        map["imageCar"] = driver?.imageCar!!
        map["capacidad"] = driver?.capacidad!!
        map["brandcar"] = driver?.brandcar!!
        map["placa"] = driver?.placa!!

        /*map["color"] = driver?.color!!
        map["color"] = driver?.color!!
        map["color"] = driver?.color!! map["brandcar"] = driver?.brandcar!!
        map["capacidad"] = driver?.capacidad!!
        map["color"] = driver?.color!!
        map["combustible"] = driver?.combustible!!
        */

        return db.document(driver?.id!!).update(map)
    }
}