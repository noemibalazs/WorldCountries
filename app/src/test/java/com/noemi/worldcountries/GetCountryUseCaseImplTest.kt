package com.noemi.worldcountries

import com.apollographql.apollo3.ApolloClient
import com.noemi.worldcountries.network.ApolloCountryClient
import com.noemi.worldcountries.usecase.GetCountryUseCase
import com.noemi.worldcountries.usecase.GetCountryUseCaseImpl
import com.noemi.worldcountries.utils.BASE_URL
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class GetCountryUseCaseImplTest {

    private val code = "HU"

    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    private lateinit var useCase: GetCountryUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        val apolloClient = ApolloClient.Builder().serverUrl(BASE_URL).build()
        useCase = GetCountryUseCaseImpl(
            apolloClient = ApolloCountryClient(apolloClient),
            dispatcher = dispatcher
        )
    }


    @Test
    fun `test get country and should be not null`() = runBlocking {
        val job = launch {
            val result = useCase.execute(code)
            result?.shouldNotBe(null)
        }

        job.cancelAndJoin()
    }


    @Test
    fun `test get country and should be null`() = runBlocking {
        val job = launch {
            val result = useCase.execute("")
            result?.shouldBe(null)
        }

        job.cancelAndJoin()
    }
}