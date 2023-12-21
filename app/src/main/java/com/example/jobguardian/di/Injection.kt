package com.example.jobguardian.di

import android.content.Context
import com.example.jobguardian.data.pref.UserModel
import com.example.jobguardian.data.pref.UserPreference
import com.example.jobguardian.data.pref.dataStore
import com.example.jobguardian.data.repository.Repository
import com.example.jobguardian.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): Repository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(pref, apiService)
    }
}