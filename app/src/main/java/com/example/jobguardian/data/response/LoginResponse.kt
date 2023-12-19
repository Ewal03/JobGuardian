package com.example.jobguardian.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
