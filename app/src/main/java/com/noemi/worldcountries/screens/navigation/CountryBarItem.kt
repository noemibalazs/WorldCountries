package com.noemi.worldcountries.screens.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.noemi.worldcountries.utils.ROUTE_FAVORITES
import com.noemi.worldcountries.utils.ROUTE_HOME

data class CountryBarItem(
    val title: String,
    val icon: ImageVector,
    val navRoute: NavRoutes
)

sealed class NavRoutes(val route: String) {
    object Home : NavRoutes(ROUTE_HOME)
    object Favorites : NavRoutes(ROUTE_FAVORITES)
}