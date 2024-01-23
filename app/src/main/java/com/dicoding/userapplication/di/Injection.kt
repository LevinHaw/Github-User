package com.dicoding.userapplication.di

import android.content.Context
import com.dicoding.userapplication.repository.FavoriteRepository
import com.dicoding.userapplication.repository.data.local.database.FavoriteRoomDatabase
import java.util.concurrent.Executors

object Injection {

    fun provideRepository(context: Context): FavoriteRepository {
        val database = FavoriteRoomDatabase.getDatabase(context)
        val dao = database.favoriteDao()
        val executorService = Executors.newSingleThreadExecutor()
        return FavoriteRepository.getInstance(dao, executorService)
    }

}