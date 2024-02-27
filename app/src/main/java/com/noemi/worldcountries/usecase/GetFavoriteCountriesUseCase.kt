package com.noemi.worldcountries.usecase

import com.noemi.worldcountries.models.Country
import kotlinx.coroutines.flow.Flow

interface GetFavoriteCountriesUseCase {

    fun execute(): Flow<List<Country>>
}