package com.exotic.myapplication.model

import com.google.gson.annotations.SerializedName

data class Users(

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("phone_number")
    val phone_number: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("uuid")
    val uuid: String


)
