package com.noemi.worldcountries.screens.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.noemi.worldcountries.screens.navigation.NavRoutes
import com.noemi.worldcountries.screens.navigation.CountryNavigationItems
import com.noemi.worldcountries.screens.viewmodel.CountryViewModel
import com.noemi.worldcountries.screens.viewmodel.FavoritesViewModel
import com.noemi.worldcountries.ui.theme.philosopherFamily

@Composable
fun CountriesApp(
    navController: NavHostController = rememberNavController(),
    countryViewModel: CountryViewModel,
    favoritesViewModel: FavoritesViewModel
) {

    Scaffold(
        content = {
            Column {
                NavigationHost(navController = navController, countryViewModel = countryViewModel, favoritesViewModel = favoritesViewModel)
            }
        },
        bottomBar = { BottomNavigationBar(navController = navController) }
    )
}

@Composable
fun NavigationHost(navController: NavHostController, countryViewModel: CountryViewModel, favoritesViewModel: FavoritesViewModel) {
    NavHost(navController = navController, startDestination = NavRoutes.Home.route) {
        composable(NavRoutes.Home.route) {
            HomeScreen(countryViewModel)
        }

        composable(NavRoutes.Favorites.route) {
            FavoritesScreen(favoritesViewModel)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
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
                        fontFamily = philosopherFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
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