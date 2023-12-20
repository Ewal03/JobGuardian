package com.example.jobguardian.ui.authenticaion.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobguardian.data.pref.UserModel
import com.example.jobguardian.data.repository.Repository
import kotlinx.coroutines.launch

class SignInViewModel(private val repository: Repository) : ViewModel() {
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

//    fun login(email: String, password: String) = repository.login(email, password)
}
