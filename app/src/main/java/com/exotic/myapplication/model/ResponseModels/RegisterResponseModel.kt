package com.exotic.myapplication.model.ResponseModels

data class RegisterResponseModel(
    val success: Boolean,
    val token: String,
    val user: User
)