package com.dicoding.userapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.userapplication.repository.FavoriteRepository
import com.dicoding.userapplication.repository.data.local.database.FavoriteUser

class FavoriteUserViewModel(private val favoriteRepository: FavoriteRepository): ViewModel() {

    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> =
        favoriteRepository.getAllFavoriteUser()

    fun getAllFavoriteUserByUsername(username: String): LiveData<FavoriteUser> =
        favoriteRepository.getAllFavoriteUserByUsername(username)

    fun insert(favoriteUser: FavoriteUser){
        favoriteRepository.insert(favoriteUser)
    }

    fun delete(favoriteUser: FavoriteUser){
        favoriteRepository.delete(favoriteUser)
    }

}