package com.example.jobguardian.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewmodel.ktx.R
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.jobguardian.data.pref.UserModel
import com.example.jobguardian.data.pref.UserPreference
import com.example.jobguardian.data.response.DataItem
import com.example.jobguardian.data.retrofit.ApiServices
import com.example.jobguardian.ui.main.DataPagingSource
import kotlinx.coroutines.flow.Flow

class Repository(
    private val apiService: ApiServices
) {
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
            apiService: ApiServices
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService)
            }.also { instance = it }
    }
}