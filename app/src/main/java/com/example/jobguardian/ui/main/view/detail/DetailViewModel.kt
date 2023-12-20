package com.example.jobguardian.ui.main.view.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.jobguardian.data.db.entity.FavoriteEntity
import com.example.jobguardian.data.db.remote.FavoriteRepository

class DetailViewModel(application: Application) : ViewModel() {

    private val repository: FavoriteRepository = FavoriteRepository(application)

    fun isFavorit(title: String): LiveData<Boolean> = repository.isFavorit(title)

    fun insertFavorit(favoriteEntity: FavoriteEntity) {
        repository.insertFavorit(favoriteEntity)
    }

    fun deleteFavorit(favoriteEntity: FavoriteEntity) {
        repository.deleteFavorit(favoriteEntity)
    }
}