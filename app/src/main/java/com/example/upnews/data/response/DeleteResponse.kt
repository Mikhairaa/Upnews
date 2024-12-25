package com.example.upnews.data.response

import com.google.gson.annotations.SerializedName

data class DeleteResponse(

	@field:SerializedName("success")
	val success: Boolean?,

	@field:SerializedName("message")
	val message: String? = null
)
