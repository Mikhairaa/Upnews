package com.example.upnews.data.response

import com.google.gson.annotations.SerializedName

data class GetProfileResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: UserProfile? = null
)

data class UserProfile(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("fotoProfil")
	val fotoProfil: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null
)
