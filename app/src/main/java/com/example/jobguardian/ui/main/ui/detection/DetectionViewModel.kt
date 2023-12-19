package com.example.jobguardian.ui.main.ui.detection

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jobguardian.data.response.DescriptionResponse
import com.example.jobguardian.data.retrofit.ApiConfig
import com.google.android.play.integrity.internal.e
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetectionViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Detection Fragment"
    }
    val text: LiveData<String> = _text

    fun sendDataToApi(logo: String, contact: String, jobDescription: String) {
        val apiService = ApiConfig.getApiService()
        val call = apiService.addDescription(logo, contact, jobDescription)
        call.enqueue(object : Callback<DescriptionResponse> {
            override fun onResponse(call: Call<DescriptionResponse>, response: Response<DescriptionResponse>) {
                val errorBody = "aaa"
                val pesan = Gson().fromJson(errorBody, DescriptionResponse::class.java)
                pesan.descriptionId?.let { Log.d("pesan", it) }
            }
            override fun onFailure(call: Call<DescriptionResponse>, t: Throwable) {
                Log.d("pesan","gagal")
            }
        })
    }
}