package com.example.upnews.data.response

import com.google.gson.annotations.SerializedName

data class UploadResponse(

	@field:SerializedName("data")
	val data: DataUpload? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataUpload(

	@field:SerializedName("no_hp")
	val noHp: String? = null,

	@field:SerializedName("no_rek")
	val noRek: String? = null,

	@field:SerializedName("id_user")
	var idUser: Int? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("lokasi")
	val lokasi: String? = null,

	@field:SerializedName("bukti")
	val bukti: String? = null,

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
	val status: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
