package com.example.jobguardian.ui.main.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jobguardian.data.pref.UserModel
import com.example.jobguardian.data.repository.Repository
import com.example.jobguardian.data.response.DataItem
import com.example.jobguardian.data.response.UserProfileResponse
import com.example.jobguardian.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel (private val repository: Repository): ViewModel() {

    private val _userProfile = MutableLiveData<UserProfileResponse>()
    val userProfile: LiveData<UserProfileResponse> get() = _userProfile

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage
    private var _isLoading = MutableLiveData<Boolean>()
//    var loading :Boolean = true
    val isLoading: LiveData<Boolean> = _isLoading
    fun getData(): LiveData<PagingData<DataItem>> {
        return repository.getData().cachedIn(viewModelScope)
    }
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
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
//                    loading=false
                } else {
                    _errorMessage.value = "Failed to get user profile"
                }
            }

            override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                _errorMessage.value = "Network error: ${t.message}"
            }
        })
    }
}