package com.example.test.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    data object Main : Screen("main")
    data object MealsList : Screen("meals/{categoryName}") {
        fun createRoute(categoryName: String) = "meals/${Uri.encode(categoryName)}"
    }
    data object MealDetail : Screen("detail/{mealId}") {
        fun createRoute(mealId: String) = "detail/$mealId"
    }
}