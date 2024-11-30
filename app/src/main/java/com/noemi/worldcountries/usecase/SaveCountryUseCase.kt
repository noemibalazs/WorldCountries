package com.noemi.worldcountries.usecase

import com.noemi.worldcountries.models.Country
import com.noemi.worldcountries.room.CountryDAO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveCountryUseCase @Inject constructor(
    private val countryDAO: CountryDAO,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(country: Country) = withContext(dispatcher) {
        countryDAO.insertCountry(country)
    }
}