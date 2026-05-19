package com.example.test.data.remote.api

import com.example.test.data.remote.model.CategoriesResponse
import com.example.test.data.remote.model.MealDetailResponse
import com.example.test.data.remote.model.MealsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {
    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): MealsResponse

    @GET("lookup.php")
    suspend fun getMealDetail(@Query("i") mealId: String): MealDetailResponse

    @GET("search.php")
    suspend fun searchMeals(@Query("s") query: String): MealDetailResponse
}