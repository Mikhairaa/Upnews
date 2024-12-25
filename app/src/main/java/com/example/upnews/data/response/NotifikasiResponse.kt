package com.example.upnews.data.response

import com.google.gson.annotations.SerializedName

data class NotifikasiResponse(

	@field:SerializedName("data")
	val dataNotifikasi: List<DataNotifikasi?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataNotifikasi(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("id_notifikasi")
	val idNotifikasi: Int? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("id_berita")
	val idBerita: Int? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
