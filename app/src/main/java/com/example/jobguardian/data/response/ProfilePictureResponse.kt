package com.example.jobguardian.data.response

import com.google.gson.annotations.SerializedName

data class ProfilePictureResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
