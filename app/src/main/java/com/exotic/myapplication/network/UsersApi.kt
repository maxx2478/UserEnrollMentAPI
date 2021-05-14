package com.exotic.myapplication.network

import com.exotic.myapplication.model.UserData
import com.exotic.myapplication.model.Users
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import javax.security.auth.callback.Callback

interface UsersApi {


    @GET("auth/getusers")
    fun getAllUsers(): Call<UserData>

    @POST("auth/register")
    fun createUser(@Body user:Users): Call<Users>



}