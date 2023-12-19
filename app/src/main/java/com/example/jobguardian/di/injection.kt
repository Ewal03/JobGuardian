package com.example.jobguardian.di

import android.content.Context
//import com.example.jobguardian.repository.StoryRepository
import com.example.jobguardian.data.pref.UserPreference
import com.example.jobguardian.data.pref.dataStore
import com.example.jobguardian.data.retrofit.ApiConfig
import com.example.jobguardian.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(pref, apiService)
    }
//    fun provideStoryRepository(context: Context): StoryRepository {
//        val pref = UserPreference.getInstance(context.dataStore)
//        val user = runBlocking { pref.getSession().first() }
//        val apiService = ApiConfig.getApiService(user.token)
//        return StoryRepository.getInstance(apiService)
//    }
}