package com.app.myapplication.models

import com.beust.klaxon.*

private val klaxon = Klaxon()

data class Driver (
    val id: String? = null,
    val name: String? = null,
    val brandcar: String? = null,
    val email: String? = null,
    val phone: String? = null,
    var image: String? = null,
    var imageCar: String? = null,
    val maxPower: String? = null,
    val modelo: String? = null,
    val capacidad: String? = null,
    val combustible: String? = null,
    val motor: String? = null,
    val color: String? = null,
    val maxVel: String? = null,
    val placa: String? = null

    ) {


    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<Driver>(json)
    }
}