package com.noemi.worldcountries.screens.countries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noemi.worldcountries.models.Country
import com.noemi.worldcountries.models.DetailedCountry
import com.noemi.worldcountries.usecase.GetCountriesUseCase
import com.noemi.worldcountries.usecase.GetCountryUseCase
import com.noemi.worldcountries.usecase.SaveCountryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val saveCountryUseCase: SaveCountryUseCase,
    private val getCountryUseCase: GetCountryUseCase
) : ViewModel() {

    private val _countriesState = MutableStateFlow(CountriesState())
    val countriesState = _countriesState.asStateFlow()

    private val _countryState = MutableStateFlow(CountryState())
    val countryState = _countryState.asStateFlow()

    init {
        initialiseCountries()
    }

    fun initialiseCountries() {
        viewModelScope.launch {
            _countriesState.update {
                it.copy(isLoading = true)
            }
            _countriesState.update {
                it.copy(
                    isLoading = false,
                    countries = getCountriesUseCase.execute()
                )
            }
        }
    }

    fun saveDisplayCountry(country: Country) {
        viewModelScope.launch {
            saveCountryUseCase.execute(country)

            _countryState.update {
                it.copy(
                    selectedCountry =
                    getCountryUseCase.execute(country.code)
                )

            }
        }
    }

    fun dismissCountry() {
        _countryState.update {
            it.copy(selectedCountry = null)
        }
    }

    data class CountryState(
        val selectedCountry: DetailedCountry? = null
    )

    data class CountriesState(
        val isLoading: Boolean = false,
        val countries: List<Country> = emptyList()
    )
}