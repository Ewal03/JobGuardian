package com.example.jobguardian.ui.main.view.profile

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jobguardian.data.pref.UpdateBiodataRequest
import com.example.jobguardian.data.response.ProfilePictureResponse
import com.example.jobguardian.data.response.UpdateBiodataResponse
import com.example.jobguardian.data.response.UserProfileResponse
import com.example.jobguardian.data.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    private val _userProfile = MutableLiveData<UserProfileResponse>()
    val userProfile: LiveData<UserProfileResponse> get() = _userProfile

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    fun getUserProfile(userId: String) {
        _isLoading.value = true
        val apiService = ApiConfig.getApiService()
        apiService.getUserProfile(userId).enqueue(object : Callback<UserProfileResponse> {
            override fun onResponse(
                call: Call<UserProfileResponse>,
                response: Response<UserProfileResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userProfile.value = response.body()
                } else {
                    _errorMessage.value = "Failed to get user profile"
                }
            }

            override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                _errorMessage.value = "Network error: ${t.message}"
            }
        })
    }

    suspend fun updateBiodataUser(userId: String, updateBiodataRequest: UpdateBiodataRequest) {
        try {
            val apiService = ApiConfig.getApiService()
            val response = withContext(Dispatchers.IO) {
                apiService.updateBiodataUser(userId, updateBiodataRequest)
            }
            if (response.message.equals("Biodata updated successfully")) {

            } else {
                _errorMessage.value = "Succes Update Data"
            }
        } catch (e: Exception) {
            _errorMessage.value = "Network error: ${e.message}"
        }
    }


    fun uploadProfilePicture(userId: String, profilePicture: MultipartBody.Part) {
        val apiService = ApiConfig.getApiService()
        apiService.uploadProfilePicture(userId, profilePicture)
            .enqueue(object : Callback<ProfilePictureResponse> {
                override fun onResponse(
                    call: Call<ProfilePictureResponse>,
                    response: Response<ProfilePictureResponse>
                ) {
                    if (response.isSuccessful) {
                        // Handle successful upload, if needed
                    } else {
                        _errorMessage.value = "Failed to upload profile picture"
                    }
                }

                override fun onFailure(call: Call<ProfilePictureResponse>, t: Throwable) {
                    _errorMessage.value = "Network error: ${t.message}"
                }
            })
    }
}
