package com.noemi.worldcountries.usecase

import com.noemi.worldcountries.models.Country
import com.noemi.worldcountries.room.CountryDAO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCountryByNameUseCaseImpl @Inject constructor(
    private val countryDAO: CountryDAO,
    private val dispatcher: CoroutineDispatcher
) : GetCountryByNameUseCase {

    override suspend fun execute(name: String): Country? = withContext(dispatcher) {
        countryDAO.getCountryByName(name)
    }
}