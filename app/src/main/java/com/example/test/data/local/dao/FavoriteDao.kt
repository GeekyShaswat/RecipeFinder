package com.example.test.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.test.data.local.entity.FavoriteMealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<FavoriteMealEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(meal: FavoriteMealEntity)

    @Delete
    suspend fun removeFavorite(meal: FavoriteMealEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE idMeal = :mealId)")
    fun isFavorite(mealId: String): Flow<Boolean>
}