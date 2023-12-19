package com.example.jobguardian.repository

import com.example.jobguardian.data.pref.UserModel
import com.example.jobguardian.data.pref.UserPreference
import com.example.jobguardian.data.retrofit.ApiServices
import kotlinx.coroutines.flow.Flow
import com.example.jobguardian.data.response.LoginResponse
import com.example.jobguardian.data.utils.ResultState
//import com.example.jobguardian.data.response.RegisterResponse
import com.google.gson.Gson
import retrofit2.HttpException
import androidx.lifecycle.liveData as livedata


class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiServices
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

//    fun register(name: String, email: String, password: String) = liveData {
//        emit(ResultState.Loading)
//        try {
//            val successResponse = apiService.register(name, email, password)
//            emit(ResultState.Success(successResponse))
//        } catch (e: HttpException) {
//            val errorBody = e.response()?.errorBody()?.string()
//            val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
//            emit(ResultState.Error(errorResponse.message))
//        }
//    }

//    fun login(email: String, password: String) = livedata {
//        emit(ResultState.Loading)
//        try {
//            val successLoginResponse = apiService.login(email, password)
//            emit(ResultState.Success(successLoginResponse))
//        } catch (e: HttpException) {
//            val errorBody = e.response()?.errorBody()?.string()
//            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
//            emit(errorResponse.msg?.let { ResultState.Error(it) })
//        }
//    }

    suspend fun logout() {
        userPreference.logout()
    }



    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiServices
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}