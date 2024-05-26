package com.noemi.worldcountries

import androidx.activity.compose.setContent
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.noemi.worldcountries.screens.composables.CountriesApp
import com.noemi.worldcountries.screens.main.CountriesActivity
import com.noemi.worldcountries.screens.navigation.NavRoutes
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class CountriesActivityTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<CountriesActivity>()

    private var navController: TestNavHostController? = null

    @Before
    fun setupCountriesApp() {
        composeRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController?.navigatorProvider?.addNavigator(ComposeNavigator())

            navController?.let {
                CountriesApp(it)
            }
        }
    }

    @Test
    fun testHomeScreen() {
        composeRule.onNodeWithStringId(R.string.label_countries).isDisplayed()
        navController?.assertCurrentRouteName(NavRoutes.Home.route)
        composeRule.onNodeWithStringId(R.string.label_home).performClick()
        navigateToFavoriteScreen()
    }

    @Test
    fun testFavoriteScreen() {
        composeRule.onNodeWithStringId(R.string.label_favorites).performClick()
        navController?.assertCurrentRouteName(NavRoutes.Favorites.route)
        navigateToHomeScreen()
    }

    @Test
    fun testAndorraCountryDisplayed() {
        composeRule.onNodeWithStringId(R.string.label_countries).isDisplayed()
        navController?.assertCurrentRouteName(NavRoutes.Home.route)
        composeRule.waitUntilExactlyOneExists(hasText(ANDORRA))
        composeRule.onNodeWithText(ANDORRA).isDisplayed()
        composeRule.onNodeWithText(ANDORRA).performClick()
    }

    @Test
    fun testCountrySearch() {
        composeRule.onNodeWithStringId(R.string.label_favorites).performClick()
        composeRule.onNodeWithStringId(R.string.label_country).performTextInput(ANDORRA)
        val down = android.view.KeyEvent(NativeKeyEvent.ACTION_DOWN, NativeKeyEvent.ACTION_DOWN)
        composeRule.onNodeWithStringId(R.string.label_country).performKeyPress(KeyEvent(down))
    }

    @Test
    fun testNavControllerNavigateUp() {
        navigateToHomeScreen()
        navController?.navigateUp()
    }

    private fun navigateToFavoriteScreen() {
        composeRule.onNodeWithStringId(R.string.label_favorites).performClick()
    }

    private fun navigateToHomeScreen() {
        composeRule.onNodeWithStringId(R.string.label_home).performClick()
    }

    companion object {
        private const val ANDORRA = "Andorra"
    }
}