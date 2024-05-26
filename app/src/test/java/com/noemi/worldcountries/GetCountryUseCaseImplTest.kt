package com.noemi.worldcountries

import com.noemi.worldcountries.models.DetailedCountry
import com.noemi.worldcountries.network.ApolloCountryClient
import com.noemi.worldcountries.usecase.GetCountryUseCase
import com.noemi.worldcountries.usecase.GetCountryUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
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
class GetCountryUseCaseImplTest {

    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()
    private val apolloClient: ApolloCountryClient = mockk()
    private lateinit var useCase: GetCountryUseCase

    private val code = "HU"
    private val detailedCountry = mockk<DetailedCountry>()

    @Before
    fun setUp() {
        useCase = GetCountryUseCaseImpl(
            apolloClient = apolloClient,
            dispatcher = dispatcher
        )
    }


    @Test
    fun `test get country and should be successful`() = runBlocking {

        coEvery { apolloClient.getCountry(code) } returns detailedCountry
        coEvery { detailedCountry.code } returns code
        coEvery { detailedCountry.name } returns "Hungary"

        useCase.execute(code)

        coVerify { apolloClient.getCountry(code) }
    }


    @Test
    fun `test get country and should be null`() = runBlocking {
        coEvery { apolloClient.getCountry("") } returns null

        useCase.execute("")

        coVerify { apolloClient.getCountry("") }
    }
}