package com.example.upnews.data.response

import com.google.gson.annotations.SerializedName

data class GetDoneResponse(

	@field:SerializedName("berita")
	val berita: List<BeritaDone?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class BeritaDone(

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
