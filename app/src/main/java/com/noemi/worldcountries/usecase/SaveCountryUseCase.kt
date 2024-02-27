package com.noemi.worldcountries.usecase

import com.noemi.worldcountries.models.Country

interface SaveCountryUseCase {

    suspend fun execute(country:Country)
}