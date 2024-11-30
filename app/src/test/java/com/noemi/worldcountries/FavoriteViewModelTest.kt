package com.noemi.worldcountries

import app.cash.turbine.test
import com.noemi.worldcountries.screens.favorites.FavoritesViewModel
import com.google.common.truth.Truth.assertThat
import com.noemi.worldcountries.usecase.GetCountryByNameUseCase
import com.noemi.worldcountries.usecase.GetFavoriteCountriesUseCase
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoriteViewModelTest {

    private val getFavoriteCountriesUseCase: GetFavoriteCountriesUseCase = mockk()
    private val getCountryByNameUseCase: GetCountryByNameUseCase = mockk()

    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: FavoritesViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = FavoritesViewModel(
            getFavoriteCountriesUseCase = getFavoriteCountriesUseCase,
            getCountryByNameUseCase = getCountryByNameUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test initialise favorite countries and should pass`() = runBlocking {
        val job = launch {
            viewModel.favoriteCountriesState.test {
                val state = awaitItem()

                val result = getFavoriteCountriesUseCase.invoke().collect()
                assertThat(state).isEqualTo(result)

                cancelAndConsumeRemainingEvents()
            }
        }

        viewModel.initialiseFavoriteCountries()

        job.cancelAndJoin()
    }


    @Test
    fun `test find country by name and should pass`() = runBlocking {
        val name = "Hungary"

        val job = launch {
            assertThat(viewModel.isSearchError).isEqualTo(false)
            viewModel.countryState.test {
                val state = awaitItem()

                val result = getCountryByNameUseCase.invoke(name)
                assertThat(state).isEqualTo(result)
                assertThat(viewModel.isSearchError).isEqualTo(false)

                cancelAndConsumeRemainingEvents()
            }
        }

        viewModel.findCountryByName()

        job.cancelAndJoin()
    }

    @Test
    fun `test dismiss country and should pass`() = runBlocking {
        val job = launch {
            viewModel.countryState.test {
                val state = awaitItem()
                assertThat(state.country).isEqualTo(null)
                cancelAndConsumeRemainingEvents()
            }
        }

        viewModel.dismissCountry()

        job.cancelAndJoin()
    }
}