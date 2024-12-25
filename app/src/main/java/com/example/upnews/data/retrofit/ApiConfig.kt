package com.example.upnews.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private const val BASE_URL = "https://upnews.ftiorganizerhub.tech/api/"


    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
//            .client(client.build())
            .build()
            .create(ApiService::class.java)
    }
}