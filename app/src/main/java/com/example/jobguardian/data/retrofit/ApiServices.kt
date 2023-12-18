package com.example.jobguardian.data.retrofit

import com.example.jobguardian.data.response.ListJobResponse
import com.example.jobguardian.data.response.UserProfileResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {
    @GET("api/getUserProfile/{userId}")
    fun getUserProfile(@Path("userId") userId: String): Call<UserProfileResponse>

    @GET("api/getAll")
    fun getcompanyData(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): ListJobResponse
}