package com.example.jobguardian.data.retrofit
import com.example.jobguardian.data.response.UserProfileResponse
import retrofit2.Call
import retrofit2.http.*
interface ApiServices {
    @GET("api/getUserProfile/{userId}")
    fun getUserProfile(@Path("userId") userId: String): Call<UserProfileResponse>
}