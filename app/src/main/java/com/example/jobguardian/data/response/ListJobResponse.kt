package com.example.jobguardian.data.response

import com.google.gson.annotations.SerializedName

data class ListJobResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("status")
	val status: String
)

data class DataItem(

	@field:SerializedName("salaryRange")
	val salaryRange: String,

	@field:SerializedName("benefits")
	val benefits: String,

	@field:SerializedName("requiredEducation")
	val requiredEducation: String,

	@field:SerializedName("requirements")
	val requirements: String,

	@field:SerializedName("employmentType")
	val employmentType: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("industry")
	val industry: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("hasCompanyLog")
	val hasCompanyLog: Boolean,

	@field:SerializedName("requiredExperience")
	val requiredExperience: String,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("companyProfile")
	val companyProfile: String,

	@field:SerializedName("department")
	val department: String,

	@field:SerializedName("telecommuting")
	val telecommuting: Boolean,

	@field:SerializedName("fraudulent")
	val fraudulent: Boolean,

	@field:SerializedName("status")
	val status: String
)
