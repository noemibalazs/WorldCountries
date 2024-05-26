package com.noemi.worldcountries

import com.noemi.worldcountries.models.Country
import com.noemi.worldcountries.room.CountryDAO
import com.noemi.worldcountries.usecase.SaveCountryUseCase
import com.noemi.worldcountries.usecase.SaveCountryUseCaseImpl
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SaveCountryUseCaseImplTest {

    private val countryDAO: CountryDAO = mockk()

    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    private lateinit var useCase: SaveCountryUseCase

    private val country = mockk<Country>()

    @Before
    fun setUp() {
        useCase = SaveCountryUseCaseImpl(
            countryDAO = countryDAO,
            dispatcher = dispatcher
        )
    }

    @Test
    fun `test save country and should be successful`() = runBlocking {
        coEvery { countryDAO.insertCountry(country) } just runs

        useCase.execute(country)

        coVerify { countryDAO.insertCountry(country) }
    }
}