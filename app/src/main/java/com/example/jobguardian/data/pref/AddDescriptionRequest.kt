package com.example.jobguardian.data.pref

import com.google.gson.annotations.SerializedName

data class AddDescriptionRequest(
    @SerializedName("logo") val logo: String,
    @SerializedName("contact") val contact: String,
    @SerializedName("job_description") val jobDescription: String
)