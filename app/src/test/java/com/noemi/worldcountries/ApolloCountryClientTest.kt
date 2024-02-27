package com.noemi.worldcountries

import com.apollographql.apollo3.ApolloClient
import com.noemi.worldcountries.network.ApolloCountryClient
import com.noemi.worldcountries.network.CountryClient
import com.noemi.worldcountries.utils.BASE_URL
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ApolloCountryClientTest {

    private lateinit var countryClient: CountryClient
    private val countryCode = "HU"
    private val country = "Hungary"
    private val capital = "Budapest"

    @Before
    fun setUp() {
        val apolloClient = ApolloClient.Builder().serverUrl(BASE_URL).build()
        countryClient = ApolloCountryClient(apolloClient)
    }

    @Test
    fun `test get countries and should be not null`() = runBlocking {
        val job = launch {
            val result = countryClient.getCountries()
            result.shouldNotBe(null)
        }

        job.cancelAndJoin()
    }

    @Test
    fun `test get country and should be Hungary`() = runBlocking {
        val job = launch {
            val result = countryClient.getCountry(countryCode)
            result?.capital shouldBe capital
            result?.name shouldBe country
        }

        job.cancelAndJoin()
    }


    @Test
    fun `test get country and should be null`() = runBlocking {
        val job = launch {
            val result = countryClient.getCountry("")
            result?.shouldBe(null)
        }

        job.cancelAndJoin()
    }

}