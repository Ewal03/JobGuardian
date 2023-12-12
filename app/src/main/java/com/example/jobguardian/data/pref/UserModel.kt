package com.example.jobguardian.data.pref

data class UserModel(
    val email: String,
    val id: String,
    val isLogin: Boolean = false
)