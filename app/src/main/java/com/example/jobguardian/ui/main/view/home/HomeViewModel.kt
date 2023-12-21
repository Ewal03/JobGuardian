package com.example.jobguardian.ui.main.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jobguardian.data.repository.Repository
import com.example.jobguardian.data.response.DataItem

class HomeViewModel (private val repository: Repository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    fun getData(): LiveData<PagingData<DataItem>> {
        return repository.getData().cachedIn(viewModelScope)
    }
}