package com.noemi.worldcountries.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.noemi.worldcountries.screens.countries.CountriesScreen
import com.noemi.worldcountries.screens.favorites.FavoritesScreen
import com.noemi.worldcountries.screens.navigation.NavRoutes
import com.noemi.worldcountries.screens.navigation.CountryNavigationItems

@Composable
fun CountriesApp(navController: NavHostController = rememberNavController()) {

    Scaffold(
        content = {
            Column {
                NavigationHost(navController = navController)
            }
        },
        bottomBar = { BottomNavigationBar(navController = navController) }
    )
}

@Composable
private fun NavigationHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavRoutes.Home.route) {
        composable(NavRoutes.Home.route) {
            CountriesScreen()
        }

        composable(NavRoutes.Favorites.route) {
            FavoritesScreen()
        }
    }
}

@Composable
private fun BottomNavigationBar(navController: NavHostController) {
    val context = LocalContext.current
    val navigationItems = CountryNavigationItems.getNavigationItems(context)

    NavigationBar {

        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        navigationItems.forEach { navigationItem ->

            NavigationBarItem(
                label = {
                    Text(
                        text = navigationItem.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                icon = { Icon(navigationItem.icon, contentDescription = null) },
                selected = currentRoute == navigationItem.navRoute.route,
                onClick = {
                    navController.navigate(navigationItem.navRoute.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}