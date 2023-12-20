package com.example.jobguardian.data.db.remote

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.jobguardian.data.db.entity.FavoriteEntity
import com.example.jobguardian.data.db.room.FavoriteDao
import com.example.jobguardian.data.db.room.FavoriteDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {

    private val favoritDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val favoritDatabase = FavoriteDatabase.getInstance(application)
        favoritDao = favoritDatabase.favoritDao()
    }

    fun getFavorit(): LiveData<List<FavoriteEntity>> {
        return favoritDao.getFavoritUser()
    }

    fun insertFavorit(favoritEntity: FavoriteEntity) {
        executorService.execute() {
            favoritDao.insertFavoritUser(favoritEntity)
        }
    }

    fun deleteFavorit(favoritEntity: FavoriteEntity) {
        executorService.execute() {
            favoritDao.deleteFavoritUser(favoritEntity)
        }
    }

    fun isFavorit(login: String) = favoritDao.isFavoritUser(login)

}