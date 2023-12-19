package com.example.jobguardian.data.response

import com.google.gson.annotations.SerializedName

data class DescriptionResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("descriptionId")
	val descriptionId: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
