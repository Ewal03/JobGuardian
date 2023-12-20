package com.example.jobguardian.ui.main.view.favorit

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.jobguardian.data.db.entity.FavoriteEntity
import com.example.jobguardian.data.db.remote.FavoriteRepository

class FavoritViewModel(application: Application) : ViewModel() {

    val repository: FavoriteRepository = FavoriteRepository(application)
    val favoritEntity: LiveData<List<FavoriteEntity>> = repository.getFavorit()

}