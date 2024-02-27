package com.noemi.worldcountries

import com.apollographql.apollo3.ApolloClient
import com.noemi.worldcountries.network.ApolloCountryClient
import com.noemi.worldcountries.type.Country
import com.noemi.worldcountries.usecase.GetCountriesUseCase
import com.noemi.worldcountries.usecase.GetCountriesUseCaseImpl
import com.noemi.worldcountries.utils.BASE_URL
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetCountriesUseCaseImplTest {

    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    private lateinit var useCase: GetCountriesUseCase

    @Before
    fun setUp() {
        val apolloClient = ApolloClient.Builder().serverUrl(BASE_URL).build()
        useCase = GetCountriesUseCaseImpl(
            apolloClient = ApolloCountryClient(apolloClient),
            dispatcher = dispatcher
        )
    }

    @Test
    fun `test get countries and should not be empty list`() = runBlocking {
        val job = launch {
            val result = useCase.execute()
            result.shouldNotBe(emptyList<Country>())
        }

        job.cancelAndJoin()
    }
}