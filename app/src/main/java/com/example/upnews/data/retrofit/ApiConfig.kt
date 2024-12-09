package com.example.upnews.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private const val BASE_URL = "http://upnews.ftiorganizerhub.tech/api/"

//    private val client = OkHttpClient.Builder()
//        .connectTimeout(600, TimeUnit.SECONDS)
//        .readTimeout(600, TimeUnit.SECONDS)
//        .writeTimeout(600, TimeUnit.SECONDS)

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
//            .client(client.build())
            .build()
            .create(ApiService::class.java)
    }
}