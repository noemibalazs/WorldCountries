package com.noemi.worldcountries.network

import com.noemi.worldcountries.models.Country
import com.noemi.worldcountries.models.DetailedCountry

interface CountryClient {

    suspend fun getCountries(): List<Country>

    suspend fun getCountry(code: String): DetailedCountry?
}