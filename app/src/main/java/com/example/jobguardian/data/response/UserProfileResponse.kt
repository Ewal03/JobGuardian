package com.example.jobguardian.data.response

import com.google.gson.annotations.SerializedName

data class UserProfileResponse(

	@field:SerializedName("userProfile")
	val userProfile: UserProfile? = null
)

data class UserProfile(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("contact")
	val contact: String? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("birthDate")
	val birthDate: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
