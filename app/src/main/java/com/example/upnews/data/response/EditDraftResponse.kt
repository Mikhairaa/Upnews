package com.example.upnews.data.response

import com.google.gson.annotations.SerializedName

data class EditDraftResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)
data class Data(
	@SerializedName("no_hp") val noHp: String? = null,
	@SerializedName("no_rek") val noRek: String? = null,
	@SerializedName("id_user") val idUser: Int? = null,
	@SerializedName("createdAt") val createdAt: String? = null,
	@SerializedName("lokasi") val lokasi: String? = null,
	@SerializedName("bukti") val bukti: String? = null,
	@SerializedName("waktu") val waktu: String? = null,
	@SerializedName("tanggal") val tanggal: String? = null,
	@SerializedName("deskripsi") val deskripsi: String? = null,
	@SerializedName("judul") val judul: String? = null,
	@SerializedName("id_berita") val idBerita: Int? = null,
	@SerializedName("status") val status: String? = null,
	@SerializedName("updatedAt") val updatedAt: String? = null
){
	fun toDataSave(): DataSave {
		return DataSave(
			noHp = this.noHp,
			noRek = this.noRek,
			idUser = this.idUser,
			createdAt = this.createdAt,
			lokasi = this.lokasi,
			bukti = this.bukti,
			waktu = this.waktu,
			tanggal = this.tanggal,
			deskripsi = this.deskripsi,
			judul = this.judul,
			idBerita = this.idBerita,
			status = this.status,
			updatedAt = this.updatedAt
		)

	}
}

