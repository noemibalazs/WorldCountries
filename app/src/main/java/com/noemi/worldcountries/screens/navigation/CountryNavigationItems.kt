package com.noemi.worldcountries.screens.navigation

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import com.noemi.worldcountries.R

object CountryNavigationItems {

    fun getNavigationItems(context: Context): List<CountryBarItem> {
        return listOf(
            CountryBarItem(
                title = context.getString(R.string.label_home),
                icon = Icons.Filled.Home,
                navRoute = NavRoutes.Home
            ),

            CountryBarItem(
                title = context.getString(R.string.label_favorites),
                icon = Icons.Filled.Favorite,
                navRoute = NavRoutes.Favorites
            )
        )
    }
}