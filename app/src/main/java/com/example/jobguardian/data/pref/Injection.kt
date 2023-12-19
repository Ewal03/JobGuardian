package com.example.jobguardian.data.pref

import android.content.Context
import com.example.jobguardian.data.repository.Repository
import com.example.jobguardian.data.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(apiService)
    }
}