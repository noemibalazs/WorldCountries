package com.noemi.worldcountries.usecase

import com.noemi.worldcountries.room.CountryDAO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import com.noemi.worldcountries.models.Country
import javax.inject.Inject

class SaveCountryUseCaseImpl @Inject constructor(
    private val countryDAO: CountryDAO,
    private val dispatcher: CoroutineDispatcher
) : SaveCountryUseCase {

    override suspend fun execute(country: Country) = withContext(dispatcher){
       countryDAO.insertCountry(country)
    }
}