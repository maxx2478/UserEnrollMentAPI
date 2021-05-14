package com.exotic.myapplication.network

import com.exotic.myapplication.model.ResponseModels.RegisterResponseModel
import com.exotic.myapplication.model.AllUsers.UserData
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST

interface UsersApi {


    @GET("auth/getusers")
    fun getAllUsers(): Call<UserData>

    @POST("auth/register")
    fun createUser(@Body map:Map<String, String>): Call<RegisterResponseModel>


    //Return url to attach with user profile
    @Multipart
    @POST("upload/images")
    fun uploadimage(@Body multipartBody: MultipartBody): Call<String>





}