package com.dicoding.userapplication.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.userapplication.di.Injection
import com.dicoding.userapplication.repository.FavoriteRepository

class FavoriteUserViewModelFactory private constructor
    (private val favoriteRepository: FavoriteRepository) : ViewModelProvider.NewInstanceFactory()
{

    companion object {
        @Volatile
        private var INSTANCE: FavoriteUserViewModelFactory? = null

        fun getInstance(context: Context): FavoriteUserViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavoriteUserViewModelFactory(Injection.provideRepository(context))
            }.also { INSTANCE = it }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteUserViewModel::class.java)) {
            return FavoriteUserViewModel(favoriteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

}