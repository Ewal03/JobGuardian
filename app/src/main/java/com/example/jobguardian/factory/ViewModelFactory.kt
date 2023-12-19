package com.example.jobguardian.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jobguardian.di.Injection
//import com.example.jobguardian.repository.StoryRepository
import com.example.jobguardian.repository.UserRepository
//import com.example.jobguardian.ui.MainViewModel
//import com.example.jobguardian.ui.addstory.AddStoryViewModel
import com.example.jobguardian.ui.authenticaion.signIn.SignInViewModel
//import com.example.jobguardian.ui.maps.MapsViewModel
//import com.example.jobguardian.ui.register.RegisterViewModel

class ViewModelFactory(
    private val repository: UserRepository,
//    private val storyRepository: StoryRepository
) : ViewModelProvider
.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
//            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
//                MainViewModel(repository, storyRepository) as T
//            }

            modelClass.isAssignableFrom(SignInViewModel::class.java) -> {
                SignInViewModel(repository) as T
            }

//            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
//                RegisterViewModel(repository) as T
//            }
//
//            modelClass.isAssignableFrom(AddStoryViewModel::class.java) -> {
//                AddStoryViewModel(storyRepository, repository) as T
//            }
//            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
//                MapsViewModel(repository, storyRepository) as T
//            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideRepository(context))
            }.also { instance = it }
    }
}