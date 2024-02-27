package com.noemi.worldcountries.usecase

import com.noemi.worldcountries.models.Country

interface GetCountriesUseCase {

    suspend fun execute():List<Country>
}