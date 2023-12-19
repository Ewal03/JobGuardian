package com.example.jobguardian.data.retrofit
import com.example.jobguardian.data.response.DescriptionResponse
import com.example.jobguardian.data.response.DetectResponse
import com.example.jobguardian.data.response.ListJobResponse
import com.example.jobguardian.data.response.UserProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
interface ApiServices {
    @GET("api/getUserProfile/{userId}")
    fun getUserProfile(@Path("userId") userId: String): Call<UserProfileResponse>

    @GET("api/getAll")
    suspend fun getAllJobs(): ListJobResponse
    @Multipart
    @POST("app/api/addDescription")
    fun addDescription(
        @Part("logo") logo: String,
        @Part("contact")contact:String,
        @Part("job_description") job_description: String
        ): Call<DescriptionResponse>

    @GET("{descriptionId}")
    fun getDetect(@Path("descriptionId")descriptionId:String):DetectResponse
}