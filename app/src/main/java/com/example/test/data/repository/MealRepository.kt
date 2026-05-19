package com.example.test.data.repository

import com.example.test.data.local.dao.FavoriteDao
import com.example.test.data.local.entity.FavoriteMealEntity
import com.example.test.data.remote.api.MealApiService
import com.example.test.data.remote.model.CategoriesResponse
import com.example.test.data.remote.model.MealDetailResponse
import com.example.test.data.remote.model.MealsResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealRepository @Inject constructor(
    private val apiService: MealApiService,
    private val favoriteDao: FavoriteDao
) {
    suspend fun getCategories(): CategoriesResponse = apiService.getCategories()

    suspend fun getMealsByCategory(category: String): MealsResponse =
        apiService.getMealsByCategory(category)

    suspend fun getMealDetail(mealId: String): MealDetailResponse =
        apiService.getMealDetail(mealId)

    suspend fun searchMeals(query: String): MealDetailResponse =
        apiService.searchMeals(query)

    fun getAllFavorites(): Flow<List<FavoriteMealEntity>> = favoriteDao.getAllFavorites()

    suspend fun addFavorite(meal: FavoriteMealEntity) = favoriteDao.addFavorite(meal)

    suspend fun removeFavorite(meal: FavoriteMealEntity) = favoriteDao.removeFavorite(meal)

    fun isFavorite(mealId: String): Flow<Boolean> = favoriteDao.isFavorite(mealId)
}