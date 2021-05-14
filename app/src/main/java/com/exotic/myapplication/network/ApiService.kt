package com.exotic.myapplication.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiService
{
    private val BASE_URL = "http://18.198.2.173:2000/api/"
    private val api:UsersApi

    init {
        api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(UsersApi::class.java)
    }

    fun getApiInstance(): UsersApi
    {
        return  api
    }




}