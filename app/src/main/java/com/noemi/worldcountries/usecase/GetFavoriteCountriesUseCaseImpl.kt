package com.noemi.worldcountries.usecase

import com.noemi.worldcountries.models.Country
import com.noemi.worldcountries.room.CountryDAO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetFavoriteCountriesUseCaseImpl @Inject constructor(
    private val countryDAO: CountryDAO,
    private val dispatcher: CoroutineDispatcher
) : GetFavoriteCountriesUseCase {

    override fun execute(): Flow<List<Country>> = countryDAO.observeCountries().flowOn(dispatcher)
}