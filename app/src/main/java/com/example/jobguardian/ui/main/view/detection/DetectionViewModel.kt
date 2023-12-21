package com.example.jobguardian.ui.main.view.detection

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jobguardian.data.pref.AddDescriptionRequest
import com.example.jobguardian.data.response.DetectResponse
import com.example.jobguardian.data.retrofit.ApiConfig
import com.example.jobguardian.data.retrofit.ApiServices
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetectionViewModel : ViewModel() {

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
                        showToast(context, ": $it")
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

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}


