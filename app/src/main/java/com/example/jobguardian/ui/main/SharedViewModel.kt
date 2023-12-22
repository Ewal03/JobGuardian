package com.example.jobguardian.ui.main
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val userId = MutableLiveData<String>().apply { value = "" }
}

