package com.noemi.worldcountries

import com.noemi.worldcountries.network.ApolloCountryClient
import com.noemi.worldcountries.usecase.GetCountriesUseCase
import com.noemi.worldcountries.usecase.GetCountriesUseCaseImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import com.noemi.worldcountries.models.Country
import io.mockk.coVerify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class GetCountriesUseCaseImplTest {

    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()
    private val apolloClient: ApolloCountryClient = mockk()

    private lateinit var useCase: GetCountriesUseCase

    private val country = mockk<Country>()
    private val countries = listOf(country)

    @Before
    fun setUp() {
        useCase = GetCountriesUseCaseImpl(
            apolloClient = apolloClient,
            dispatcher = dispatcher
        )
    }

    @Test
    fun `test get countries and should not be empty list`() = runBlocking {

        coEvery { apolloClient.getCountries() } returns countries

        useCase.execute()

        coVerify { apolloClient.getCountries() }
    }

    @Test
    fun `test get countries and should be empty `() = runBlocking {

        coEvery { apolloClient.getCountries() } returns emptyList()

        useCase.execute()

        coVerify { apolloClient.getCountries() }
    }
}