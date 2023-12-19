package com.example.jobguardian.data.response

import com.google.gson.annotations.SerializedName

data class DetectResponse(

	@field:SerializedName("update_status")
	val updateStatus: String? = null,

	@field:SerializedName("prediction")
	val prediction: String? = null
)
