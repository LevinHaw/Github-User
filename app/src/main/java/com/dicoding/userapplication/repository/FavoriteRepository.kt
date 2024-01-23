package com.dicoding.userapplication.repository

import androidx.lifecycle.LiveData
import com.dicoding.userapplication.repository.data.local.database.FavoriteDao
import com.dicoding.userapplication.repository.data.local.database.FavoriteUser
import java.util.concurrent.ExecutorService

class FavoriteRepository private constructor(
    private val favoriteDao: FavoriteDao,
    private val executorService: ExecutorService
)
{

    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> = favoriteDao.getAllFavoriteUser()

    fun getAllFavoriteUserByUsername(username: String): LiveData<FavoriteUser> =
        favoriteDao.getAllFavoriteUserByUsername(username)

    fun insert(favoriteUser: FavoriteUser){
        executorService.execute { favoriteDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: FavoriteUser){
        executorService.execute { favoriteDao.delete(favoriteUser) }
    }

    companion object {
        @Volatile
        private var INSTANCE: FavoriteRepository? = null

        fun getInstance(favoriteDao: FavoriteDao, executorService: ExecutorService): FavoriteRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavoriteRepository(favoriteDao, executorService).also { INSTANCE = it }
            }
        }
    }

}