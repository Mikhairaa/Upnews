package com.example.upnews.data.retrofit

import com.example.upnews.data.response.BeritaResponse
import com.example.upnews.data.response.HomepageResponse
import com.example.upnews.data.response.LoginResponse
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ApiService {
    @GET("berita/done")
    suspend fun getBerita(): BeritaResponse

    @GET("berita/proses")
    suspend fun getBeritaProses(@Header("Authorization") token: String): BeritaResponse

    @POST("login")
    suspend fun login(@Field("email") email: String, @Field("password") password: String): LoginResponse

    @GET("berita/homepage")
    suspend fun getBeritaHomepage(@Header("Authorization") token: String): HomepageResponse


//    @PATCH
}