package com.example.test.data.remote.model

import com.google.gson.annotations.SerializedName

data class MealsResponse(
    @SerializedName("meals") val meals: List<MealSummary>?
)

data class MealSummary(
    @SerializedName("strMeal") val strMeal: String,
    @SerializedName("strMealThumb") val strMealThumb: String,
    @SerializedName("idMeal") val idMeal: String,
    @SerializedName("strArea") val strArea: String? = null,
    @SerializedName("strCountry") val strCountry: String? = null
)