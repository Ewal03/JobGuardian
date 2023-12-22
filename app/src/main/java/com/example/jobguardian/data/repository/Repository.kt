package com.example.jobguardian.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.jobguardian.data.pref.UserModel
import com.example.jobguardian.data.pref.UserPreference
import com.example.jobguardian.data.response.DataItem
import com.example.jobguardian.data.retrofit.ApiServices
import com.example.jobguardian.ui.main.view.home.DataPagingSource
import kotlinx.coroutines.flow.Flow

class Repository(
    private val userPreference: UserPreference,
    private val apiService: ApiServices
) {
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    fun getData(): LiveData<PagingData<DataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                DataPagingSource(apiService)
            }
        ).liveData
    }
    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiServices
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(userPreference, apiService)
            }.also { instance = it }
    }
}