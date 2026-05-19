package com.example.test.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.test.ui.screens.detail.MealDetailScreen
import com.example.test.ui.screens.main.MainScreen
import com.example.test.ui.screens.meals.MealsScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) {
            MainScreen(
                onCategoryClick = { categoryName ->
                    navController.navigate(Screen.MealsList.createRoute(categoryName))
                },
                onMealClick = { mealId ->
                    navController.navigate(Screen.MealDetail.createRoute(mealId))
                }
            )
        }
        composable(
            route = Screen.MealsList.route,
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            MealsScreen(
                categoryName = categoryName,
                onMealClick = { mealId ->
                    navController.navigate(Screen.MealDetail.createRoute(mealId))
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.MealDetail.route,
            arguments = listOf(navArgument("mealId") { type = NavType.StringType })
        ) {
            MealDetailScreen(onBack = { navController.popBackStack() })
        }
    }
}