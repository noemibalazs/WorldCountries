package com.noemi.worldcountries.usecase

import com.noemi.worldcountries.models.DetailedCountry

interface GetCountryUseCase {

    suspend fun execute(code: String):  DetailedCountry?
}