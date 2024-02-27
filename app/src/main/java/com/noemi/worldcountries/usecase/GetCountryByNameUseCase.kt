package com.noemi.worldcountries.usecase

import com.noemi.worldcountries.models.Country

interface GetCountryByNameUseCase {

    suspend fun execute(name: String): Country?
}