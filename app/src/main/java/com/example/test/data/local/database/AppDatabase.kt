package com.example.test.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.test.data.local.dao.FavoriteDao
import com.example.test.data.local.entity.FavoriteMealEntity

@Database(entities = [FavoriteMealEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}