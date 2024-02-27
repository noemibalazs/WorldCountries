package com.noemi.worldcountries

import app.cash.turbine.test
import com.noemi.worldcountries.models.Country
import com.noemi.worldcountries.models.DetailedCountry
import com.noemi.worldcountries.screens.viewmodel.CountryViewModel
import com.google.common.truth.Truth.assertThat
import com.noemi.worldcountries.usecase.GetCountriesUseCase
import com.noemi.worldcountries.usecase.GetCountryUseCase
import com.noemi.worldcountries.usecase.SaveCountryUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CountryViewModelTest {

    @Mock
    private lateinit var getCountriesUseCase: GetCountriesUseCase

    @Mock
    private lateinit var saveCountryUseCase: SaveCountryUseCase

    @Mock
    private lateinit var getCountryUseCase: GetCountryUseCase

    @Mock
    private lateinit var country: Country

    @Mock
    private lateinit var detailedCountry: DetailedCountry

    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: CountryViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.openMocks(this)

        viewModel = CountryViewModel(
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
            viewModel.countriesState.test {
                val state = awaitItem()
                assertThat(false).isEqualTo(state.isLoading)
                assertThat(true).isEqualTo(state.countries.isNotEmpty())
                cancelAndConsumeRemainingEvents()
            }
        }

        viewModel.initialiseCountries()

        job.cancelAndJoin()
    }

    @Test
    fun `test get country and should pass`() = runBlocking {
        val job = launch {
            viewModel.countryState.test {
                val state = awaitItem()
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