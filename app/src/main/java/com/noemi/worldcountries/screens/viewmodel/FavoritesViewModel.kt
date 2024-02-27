package com.noemi.worldcountries.screens.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noemi.worldcountries.models.Country
import com.noemi.worldcountries.usecase.GetCountryByNameUseCase
import com.noemi.worldcountries.usecase.GetFavoriteCountriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getCountryByNameUseCase: GetCountryByNameUseCase,
    private val getFavoriteCountriesUseCase: GetFavoriteCountriesUseCase
) : ViewModel() {

    private val _favoriteCountriesState = MutableStateFlow(emptyList<Country>())
    val favoriteCountriesState = _favoriteCountriesState.asStateFlow()

    private val _countryState = MutableStateFlow(CountryState())
    val countryState = _countryState.asStateFlow()

    var searchedCountryName by mutableStateOf("")

    init {
        initialiseFavoriteCountries()
    }

    fun initialiseFavoriteCountries() {
        viewModelScope.launch {
            getFavoriteCountriesUseCase.execute().onEach {
                _favoriteCountriesState.value = it
            }.collect()
        }
    }

    fun searchingCountry(countryName: String) {
        updateSearchedCountryName(countryName)

        viewModelScope.launch {
            _countryState.update {
                it.copy(
                    country = getCountryByNameUseCase.execute(countryName)
                )
            }
        }
    }

    fun dismissCountry() {
        viewModelScope.launch {
            _countryState.update { it.copy(country = null) }
        }
    }

    fun updateSearchedCountryName(countryName: String) {
        searchedCountryName = countryName
    }

    data class CountryState(
        val country: Country? = null
    )
}