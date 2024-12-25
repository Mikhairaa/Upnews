package com.example.upnews.data.retrofit

import com.example.upnews.data.response.HomepageResponse
import com.example.upnews.data.response.ChangePasswordResponse
import com.example.upnews.data.response.NotifikasiResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface ApiService {

    @GET("user/homepage")
    suspend fun getHomepageData(@Header("Authorization") token: String): HomepageResponse

    @FormUrlEncoded
    @POST("user/changePassword")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Field("passwordLama") passwordLama: String,
        @Field("passwordBaru") passwordBaru: String,
        @Field("konfirmasiPassword") konfirmasiPassword: String
    ): ChangePasswordResponse

    @GET("berita/notifikasi") // Sesuaikan dengan endpoint yang sesuai
    suspend fun getNotifikasi(@Header("Authorization") token: String): NotifikasiResponse
}