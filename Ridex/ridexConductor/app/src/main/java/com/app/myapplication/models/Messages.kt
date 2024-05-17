package com.app.myapplication.models

import com.beust.klaxon.*

private val klaxon = Klaxon()

data class Messages(
    val id: String? = null,
    val owner: Array<Boolean>,
    val chat: Array<String>
){
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<Messages>(json)
    }
}
