package com.example.test.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.test.navigation.BottomNavItem
import com.example.test.ui.screens.category.CategoryScreen
import com.example.test.ui.screens.favorites.FavoritesScreen
import com.example.test.ui.screens.search.SearchScreen

@Composable
fun MainScreen(
    onCategoryClick: (String) -> Unit,
    onMealClick: (String) -> Unit
) {
    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(
        BottomNavItem.Category,
        BottomNavItem.Search,
        BottomNavItem.Favorites
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = currentRoute == item.route,
                        onClick = {
                            if (currentRoute != item.route) {
                                bottomNavController.navigate(item.route) {
                                    popUpTo(BottomNavItem.Category.route) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavItem.Category.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Category.route) {
                CategoryScreen(onCategoryClick = onCategoryClick)
            }
            composable(BottomNavItem.Search.route) {
                SearchScreen(onMealClick = onMealClick)
            }
            composable(BottomNavItem.Favorites.route) {
                FavoritesScreen(onMealClick = onMealClick)
            }
        }
    }
}