package com.noemi.worldcountries

import app.cash.turbine.test
import com.noemi.worldcountries.models.Country
import com.noemi.worldcountries.screens.countries.CountriesViewModel
import com.google.common.truth.Truth.assertThat
import com.noemi.worldcountries.usecase.GetCountriesUseCase
import com.noemi.worldcountries.usecase.GetCountryUseCase
import com.noemi.worldcountries.usecase.SaveCountryUseCase
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CountriesViewModelTest {

    private val getCountriesUseCase: GetCountriesUseCase = mockk()
    private val saveCountryUseCase: SaveCountryUseCase = mockk()
    private val getCountryUseCase: GetCountryUseCase = mockk()
    private val country: Country = mockk()

    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: CountriesViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = CountriesViewModel(
            getCountriesUseCase = getCountriesUseCase,
            saveCountryUseCase = saveCountryUseCase,
            getCountryUseCase = getCountryUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test initialise countries and should pass`() = runBlocking {
        val job = launch {

            assertThat(viewModel.countriesState.value.isLoading).isTrue()

            viewModel.countriesState.test {
                val state = awaitItem()

                assertThat(state.isLoading).isFalse()
                val countries = getCountriesUseCase.invoke()
                assertThat(state.countries).isEqualTo(countries)

                cancelAndConsumeRemainingEvents()
            }
        }

        viewModel.initialiseCountries()

        job.cancelAndJoin()
    }

    @Test
    fun `test get country and should pass`() = runBlocking {
        val job = launch {
            saveCountryUseCase.invoke(country)

            viewModel.countryState.test {
                val state = awaitItem()

                val detailedCountry = getCountryUseCase.invoke(country.code)
                assertThat(state.selectedCountry).isEqualTo(detailedCountry)

                cancelAndConsumeRemainingEvents()
            }
        }

        viewModel.saveDisplayCountry(country)

        job.cancelAndJoin()
    }


    @Test
    fun `test dismiss country and should pass`() = runBlocking {
        val job = launch {
            viewModel.countryState.test {
                val state = awaitItem()
                assertThat(state.selectedCountry).isEqualTo(null)
                cancelAndConsumeRemainingEvents()
            }
        }

        viewModel.dismissCountry()

        job.cancelAndJoin()
    }

}