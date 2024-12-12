package com.example.upnews.data.retrofit

import com.example.upnews.data.response.BeritaResponse
import com.example.upnews.data.response.GetDraftResponse
import com.example.upnews.data.response.HomepageResponse
import com.example.upnews.data.response.LoginResponse
import com.example.upnews.data.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("user/")
    suspend fun register(
        @Field("nama") nama: String,
        @Field("email") email: String,
        @Field("alamat") alamat: String,
        @Field("password") password: String
    ): RegisterResponse

    @GET("berita/draft")
    suspend fun getDraftBerita(@Header("Authorization") token: String): GetDraftResponse

    @GET("berita/done")
    suspend fun getBerita(@Header("Authorization") token: String): BeritaResponse

    @GET("berita/proses")
    suspend fun getBeritaProses(@Header("Authorization") token: String): BeritaResponse


    @GET("user/homepage")
    suspend fun getBeritaHomepage(): HomepageResponse


//    @PATCH
}