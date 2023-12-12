package com.example.jobguardian.data.response

import com.google.gson.annotations.SerializedName

data class UpdateBiodataResponse(

	@field:SerializedName("updatedFields")
	val updatedFields: UpdatedFields? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class UpdatedFields(

	@field:SerializedName("contact")
	val contact: String? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("birthDate")
	val birthDate: String? = null
)
