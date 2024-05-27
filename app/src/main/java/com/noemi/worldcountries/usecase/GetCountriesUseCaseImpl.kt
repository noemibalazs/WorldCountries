package com.noemi.worldcountries.usecase

import com.noemi.worldcountries.models.Country
import com.noemi.worldcountries.network.CountryClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCountriesUseCaseImpl @Inject constructor(
    private val apolloClient: CountryClient,
    private val dispatcher: CoroutineDispatcher
) : GetCountriesUseCase {

    override suspend fun execute(): List<Country> = withContext(dispatcher) {
        apolloClient.getCountries().sortedBy { it.name }
    }
}