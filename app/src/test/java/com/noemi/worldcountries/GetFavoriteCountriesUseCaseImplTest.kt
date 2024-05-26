package com.noemi.worldcountries

import com.noemi.worldcountries.models.Country
import com.noemi.worldcountries.room.CountryDAO
import com.noemi.worldcountries.usecase.GetFavoriteCountriesUseCase
import com.noemi.worldcountries.usecase.GetFavoriteCountriesUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class GetFavoriteCountriesUseCaseImplTest {

    private val countryDAO: CountryDAO = mockk()

    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    private lateinit var useCase: GetFavoriteCountriesUseCase

    private val country = mockk<Country>()
    private val countries = listOf(country)

    @Before
    fun setUp() {
        useCase = GetFavoriteCountriesUseCaseImpl(
            dispatcher = dispatcher,
            countryDAO = countryDAO
        )
    }

    @Test
    fun `test get favorite countries and should be successful`() = runBlocking {

        coEvery { countryDAO.observeCountries() } returns flowOf(countries)
        coEvery { countries.first().name } returns "Hungary"

        useCase.execute()

        coVerify { countryDAO.observeCountries() }
    }

    @Test
    fun `test get favorite countries and should be empty list`() = runBlocking {

        coEvery { countryDAO.observeCountries() } returns emptyFlow()

        useCase.execute()

        coVerify { countryDAO.observeCountries() }
    }
}