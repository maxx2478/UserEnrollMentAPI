package com.exotic.myapplication.network

import com.exotic.myapplication.model.ResponseModels.RegisterResponseModel
import com.exotic.myapplication.model.AllUsers.UserData
import com.exotic.myapplication.model.ResponseModels.FileModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface UsersApi {


    @GET("auth/getusers")
    fun getAllUsers(): Call<UserData>

    @POST("auth/register")
    fun createUser(@Body map:Map<String, String>): Call<RegisterResponseModel>


    //Return url to attach with user profile
    @Multipart
    @POST("upload/images")
    fun uploadimage(@Part image: MultipartBody.Part): Call<String>





}