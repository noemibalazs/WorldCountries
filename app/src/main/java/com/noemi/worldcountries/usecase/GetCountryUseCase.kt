package com.noemi.worldcountries.usecase

import com.noemi.worldcountries.models.DetailedCountry
import com.noemi.worldcountries.network.CountryClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCountryUseCase @Inject constructor(
    private val apolloClient: CountryClient,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(code: String): DetailedCountry? = withContext(dispatcher) {
        apolloClient.getCountry(code)
    }
}