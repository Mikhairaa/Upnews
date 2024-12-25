package com.example.upnews.data.retrofit

import com.example.upnews.data.response.DataSave
import com.example.upnews.data.response.DataUpload
import com.example.upnews.data.response.DeleteResponse
import com.example.upnews.data.response.GetDraftResponse
import com.example.upnews.data.response.LoginResponse
import com.example.upnews.data.response.RegisterResponse
import com.example.upnews.data.response.SaveDraftResponse
import com.example.upnews.data.response.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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

    @DELETE("berita/delete/{id}")
    suspend fun deleteBerita(
        @Header("Authorization") token: String,
        @Path("id") id_berita: String
    ): DeleteResponse

    @POST("berita/draft")
    suspend fun saveDraft(
        @Header("Authorization") token: String,
        @Body draftData: DataSave
    ): SaveDraftResponse

    @Multipart
    @POST("berita/upload")
    suspend fun upload(
        @Header("Authorization") token: String,
        @Part("data") data: RequestBody, // JSON data sebagai RequestBody
        @Part bukti: MultipartBody.Part? // File opsional
    ): UploadResponse

}