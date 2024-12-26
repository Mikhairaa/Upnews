package com.example.upnews.data.response

import com.google.gson.annotations.SerializedName

data class HomepageResponse(

    @field:SerializedName("berita")
    val berita: List<BeritaHome?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("user")
    val user: UserHome? = null
)

data class UserHome(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("email")
    val email: String? = null
)

data class BeritaHome(

    @field:SerializedName("lokasi")
    val lokasi: String? = null,

    @field:SerializedName("waktu")
    val waktu: String? = null,

    @field:SerializedName("tanggal")
    val tanggal: String? = null,

    @field:SerializedName("deskripsi")
    val deskripsi: String? = null,

    @field:SerializedName("judul")
    val judul: String? = null,

    @field:SerializedName("id_berita")
    val idBerita: Int? = null,

    @field:SerializedName("status")
    val status: String? = null
)