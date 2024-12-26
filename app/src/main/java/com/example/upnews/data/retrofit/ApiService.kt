package com.example.upnews.data.retrofit

import com.example.upnews.data.response.HomepageResponse
import com.example.upnews.data.response.ChangePasswordResponse
import com.example.upnews.data.response.NotifikasiResponse

import com.example.upnews.data.response.GetAllResponse
import com.example.upnews.data.response.GetDoneResponse
import com.example.upnews.data.response.DataSave
import com.example.upnews.data.response.DataUpload
import com.example.upnews.data.response.DeleteResponse
import com.example.upnews.data.response.GetDraftResponse
import com.example.upnews.data.response.GetOnProgressResponse
import com.example.upnews.data.response.GetProfileResponse
import com.example.upnews.data.response.GetRejectedResponse
import com.example.upnews.data.response.LoginResponse
import com.example.upnews.data.response.RegisterResponse
import com.example.upnews.data.response.UserProfile
import retrofit2.http.Body
import com.example.upnews.data.response.SaveDraftResponse
import com.example.upnews.data.response.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Part

interface ApiService {

    @GET("user/homepage")
    suspend fun getHomepageData(@Header("Authorization") token: String): HomepageResponse

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

    @PATCH("user/updateProfil/{id}")
    suspend fun updateProfil(
        @Header("Authorization") token: String,
        @Path("id") id: Int, // ID pengguna dikirim melalui URL
        @Body body: UserProfile // Menggunakan UserProfile sebagai body
    ): GetProfileResponse

    @Multipart
    @POST("berita/upload")
    suspend fun upload(
        @Header("Authorization") token: String,
        @Part("data") data: RequestBody, // JSON data sebagai RequestBody
        @Part bukti: MultipartBody.Part? // File opsional
    ): UploadResponse

    @GET("user/profile")
    suspend fun getUserProfile(@Header("Authorization") token: String): GetProfileResponse

    @POST("user/changePassword")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Field("passwordLama") passwordLama: String,
        @Field("passwordBaru") passwordBaru: String,
        @Field("konfirmasiPassword") konfirmasiPassword: String
    ): ChangePasswordResponse

    @GET("berita/notifikasi") // Sesuaikan dengan endpoint yang sesuai
    suspend fun getNotifikasi(@Header("Authorization") token: String): NotifikasiResponse

    @GET("berita/detail")
    suspend fun getDetailBerita(@Header("Authorization") token: String): GetDraftResponse
}
