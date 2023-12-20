package com.example.jobguardian.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jobguardian.ui.main.view.detail.DetailViewModel
import com.example.jobguardian.ui.main.view.favorit.FavoritViewModel

class FavoritViewModelFactory(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: FavoritViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): FavoritViewModelFactory {
            if (INSTANCE == null) {
                synchronized(FavoritViewModelFactory::class.java) {
                    INSTANCE = FavoritViewModelFactory(application)
                }
            }
            return INSTANCE as FavoritViewModelFactory
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FavoritViewModel::class.java) -> {
                FavoritViewModel(application) as T
            }

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(application) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel: " + modelClass.name)
        }
    }

}