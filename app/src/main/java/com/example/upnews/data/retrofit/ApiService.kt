package com.example.upnews.data.retrofit


import com.example.upnews.data.response.GetAllResponse
import com.example.upnews.data.response.GetDoneResponse
import com.example.upnews.data.response.GetDraftResponse
import com.example.upnews.data.response.GetOnProgressResponse
import com.example.upnews.data.response.GetProfileResponse
import com.example.upnews.data.response.GetRejectedResponse
import com.example.upnews.data.response.LoginResponse
import com.example.upnews.data.response.RegisterResponse
import com.example.upnews.data.response.UserProfile
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

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

    @GET("user/profile")
    suspend fun getProfile(@Header("Authorization") token: String): GetProfileResponse

    @GET("berita/all")
    suspend fun getBeritaAll(@Header("Authorization") token: String): GetAllResponse

    @GET("berita/onprogress")
    suspend fun getBeritaOnProgress(@Header("Authorization") token: String): GetOnProgressResponse

    @GET("berita/rejected")
    suspend fun getBeritaRejected(@Header("Authorization") token: String): GetRejectedResponse

    @GET("berita/done")
    suspend fun getBeritaDone(@Header("Authorization") token: String): GetDoneResponse

    @PATCH("user/updateProfil/{id}")
    suspend fun updateProfil(
        @Header("Authorization") token: String,
        @Path("id") id: Int, // ID pengguna dikirim melalui URL
        @Body body: UserProfile // Menggunakan UserProfile sebagai body
    ): GetProfileResponse

    @GET("user/profile")
    suspend fun getUserProfile(@Header("Authorization") token: String): GetProfileResponse

}