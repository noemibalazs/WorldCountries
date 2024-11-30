package com.noemi.worldcountries.screens.favorites

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
    val favoriteCountriesState = _favoriteCountriesState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    private val _countryState = MutableStateFlow(CountryState())
    val countryState = _countryState.asStateFlow()

    var searchedCountryName by mutableStateOf("")
    var isSearchError by mutableStateOf(false)

    init {
        initialiseFavoriteCountries()
    }

    fun initialiseFavoriteCountries() {
        viewModelScope.launch {
            getFavoriteCountriesUseCase.invoke().onEach { countries ->
                _favoriteCountriesState.value = countries.sortedBy { it.name }
            }.collect()
        }
    }

    fun findCountryByName() {
        viewModelScope.launch {
            val result = getCountryByNameUseCase.invoke(searchedCountryName)
            _countryState.update { it.copy(country = result) }
            isSearchError = result == null
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

    fun updateErrorState(state: Boolean) {
        isSearchError = state
    }

    data class CountryState(
        val country: Country? = null
    )
}