package com.noemi.worldcountries.network

import com.apollographql.apollo3.ApolloClient
import com.noemi.worldcountries.CountriesQuery
import com.noemi.worldcountries.CountryQuery
import com.noemi.worldcountries.models.Country
import com.noemi.worldcountries.models.DetailedCountry
import com.noemi.worldcountries.utils.toCountry
import com.noemi.worldcountries.utils.toDetailedCountry
import javax.inject.Inject

class ApolloCountryClient @Inject constructor(private val apolloClient: ApolloClient) : CountryClient {

    override suspend fun getCountries(): List<Country> =
        apolloClient.query(CountriesQuery())
            .execute()
            .data?.countries?.map { it.toCountry() } ?: emptyList()


    override suspend fun getCountry(code: String): DetailedCountry? =
        apolloClient.query(CountryQuery(code))
            .execute()
            .data?.country?.toDetailedCountry()
}