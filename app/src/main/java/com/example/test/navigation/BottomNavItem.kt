package com.example.test.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    data object Category : BottomNavItem("category", Icons.Default.RestaurantMenu, "Category")
    data object Search : BottomNavItem("search", Icons.Default.Search, "Search")
    data object Favorites : BottomNavItem("favorites", Icons.Default.Favorite, "Favorites")
}