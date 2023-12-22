package com.example.jobguardian.ui.main.view.detection

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jobguardian.data.pref.AddDescriptionRequest
import com.example.jobguardian.data.repository.Repository
import com.example.jobguardian.data.response.DetectResponse
import com.example.jobguardian.data.response.UserProfileResponse
import com.example.jobguardian.data.retrofit.ApiConfig
import com.example.jobguardian.data.retrofit.ApiServices
import com.example.jobguardian.ui.authenticaion.WelcomeActivity
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetectionViewModel  (private val repository: Repository): ViewModel() {
    private val _userProfile = MutableLiveData<UserProfileResponse>()

    private val _jobOffer = MutableLiveData<Boolean>()
    val jobOffer: LiveData<Boolean> get() = _jobOffer
    val userProfile: LiveData<UserProfileResponse> get() = _userProfile

    private val _detectionSuccess = MutableLiveData<Boolean>()
    val detectionSuccess: LiveData<Boolean> get() = _detectionSuccess
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _text = MutableLiveData<String>().apply {
        value = "This is Detection Fragment"
    }
    val text: LiveData<String> = _text

    private val _sendDataStatus = MutableLiveData<String>()
    val sendDataStatus: LiveData<String> = _sendDataStatus


    fun sendDataToApi(logo: Boolean, contact: Boolean, jobDescription: String, context: Context) {
        val apiService = ApiConfig.getApiService()

        val logoString = logo.toString()
        val contactString = contact.toString()

        val addDescriptionRequest = AddDescriptionRequest(logoString, contactString, jobDescription)
        val call = apiService.addDescription(addDescriptionRequest)

        call.enqueue(object : Callback<DetectResponse> {
            override fun onResponse(call: Call<DetectResponse>, response: Response<DetectResponse>) {
                if (response.isSuccessful) {
                    val detectResponse = response.body()
                    detectResponse?.predictedJob?.let {
                        val teks=it.toString()
                        if(teks.equals("Genuine Job Offer")){
                            _jobOffer.value=true
                        }else{
                            _jobOffer.value=false
                        }
                        _detectionSuccess.value = true
                    }
                } else {
                    Log.d("pesan", "Gagal dengan kode: ${response.code()}")
                    _sendDataStatus.value = "Gagal mengirim data dengan kode: ${response.code()}"
                    showToast(context, "Gagal mengirim data dengan kode: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<DetectResponse>, t: Throwable) {
                Log.d("pesan", "Gagal: ${t.message}")
                _sendDataStatus.value = "Gagal mengirim data: ${t.message}"

                showToast(context, "Gagal mengirim data: ${t.message}")
            }
        })
    }
    fun getUserProfile(userId: String) {
        val apiService = ApiConfig.getApiService()
        apiService.getUserProfile(userId).enqueue(object : Callback<UserProfileResponse> {
            override fun onResponse(
                call: Call<UserProfileResponse>,
                response: Response<UserProfileResponse>
            ) {
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


    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}


