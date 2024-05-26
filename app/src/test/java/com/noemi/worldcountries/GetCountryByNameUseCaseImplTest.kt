package com.noemi.worldcountries

import com.noemi.worldcountries.models.Country
import com.noemi.worldcountries.room.CountryDAO
import com.noemi.worldcountries.usecase.GetCountryByNameUseCase
import com.noemi.worldcountries.usecase.GetCountryByNameUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class GetCountryByNameUseCaseImplTest {

    private val countryDAO: CountryDAO = mockk()

    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    private lateinit var useCase: GetCountryByNameUseCase

    private val name = "Hungary"
    private val country = mockk<Country>()

    @Before
    fun setUp() {
        useCase = GetCountryByNameUseCaseImpl(
            countryDAO = countryDAO,
            dispatcher = dispatcher
        )
    }

    @Test
    fun `test get country by name and should be not null`() = runBlocking {
        coEvery { countryDAO.getCountryByName(name) } returns country
        coEvery { country.name } returns name
        coEvery { country.code } returns "HU"

        useCase.execute(name)

        coVerify { countryDAO.getCountryByName(name) }
    }


    @Test
    fun `test get country by name and should be null`() = runBlocking {
        coEvery { countryDAO.getCountryByName("") } returns null

        useCase.execute("")

        coVerify { countryDAO.getCountryByName("") }
    }
}