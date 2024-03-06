package com.noemi.worldcountries.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
fun CountriesComposedTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {

//    val useDynamicColour = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    val colorScheme = when {
//        useDynamicColour && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
//        useDynamicColour && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
        darkTheme -> darkScheme
        else -> lightScheme
    }

    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

private val lightScheme = lightColorScheme(
    primary = Scarlet40,
    onPrimary = Color.White,
    primaryContainer = Scarlet90,
    onPrimaryContainer = Scarlet10,
    secondary = Purple40,
    onSecondary = Color.White,
    secondaryContainer = Purple90,
    onSecondaryContainer = Purple10,
    error = Black40,
    onError = Color.White,
    errorContainer = Black90,
    onErrorContainer = Black10,
    background = Grey90,
    onBackground = Grey10,
    surface = PurpleGrey90,
    onSurface = PurpleGrey30,
    inverseSurface = Grey20,
    inverseOnSurface = Grey95,
    surfaceVariant = PurpleGrey90,
    onSurfaceVariant = PurpleGrey30,
    outline = PurpleGrey30
)

private val darkScheme = darkColorScheme(
    primary = Scarlet80,
    onPrimary = Scarlet20,
    primaryContainer = Scarlet30,
    onPrimaryContainer = Scarlet90,
    inversePrimary = Scarlet40,
    secondary = Purple80,
    onSecondary = Purple20,
    secondaryContainer = Purple30,
    onSecondaryContainer = Purple90,
    error = Black80,
    onError = Black20,
    errorContainer = Black30,
    onErrorContainer = Black90,
    background = Grey10,
    onBackground = Grey90,
    surface = PurpleGrey30,
    onSurface = PurpleGrey80,
    inverseSurface = Grey90,
    inverseOnSurface = Grey10,
    surfaceVariant = PurpleGrey30,
    onSurfaceVariant = PurpleGrey80,
    outline = PurpleGrey80
)

