package com.example.upnews.data.response

import com.google.gson.annotations.SerializedName

data class DetailBeritaResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("error")
	val error: Boolean? = null
)
