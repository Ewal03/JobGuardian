package com.example.jobguardian.data.response

import com.google.gson.annotations.SerializedName

data class UserProfileResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("userProfile")
	val userProfile: UserProfile? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class UserProfile(

	@field:SerializedName("profilePicture")
	var profilePicture: String? = null,

	@field:SerializedName("contact")
	val contact: String? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("birthDate")
	val birthDate: String? = null
)
