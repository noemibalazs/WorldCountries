package com.noemi.worldcountries.screens.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.noemi.worldcountries.screens.composables.CountriesApp
import com.noemi.worldcountries.ui.theme.CountriesComposedTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CountriesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CountriesComposedTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    CountriesApp(
                        countryViewModel = hiltViewModel(),
                        favoritesViewModel = hiltViewModel()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CountriesComposedTheme {}
}