package com.example.jobguardian.data.retrofit
import com.example.jobguardian.data.pref.AddDescriptionRequest
import com.example.jobguardian.data.response.DetectResponse
import com.example.jobguardian.data.response.ListJobResponse
import com.example.jobguardian.data.response.UserProfileResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
interface ApiServices {
    @GET("api/getUserProfile/{userId}")
    fun getUserProfile(@Path("userId") userId: String): Call<UserProfileResponse>

    @GET("api/getAll")
    suspend fun getAllJobs(): ListJobResponse
    @POST("api/addDescription")
    fun addDescription(@Body request: AddDescriptionRequest): Call<DetectResponse>


}