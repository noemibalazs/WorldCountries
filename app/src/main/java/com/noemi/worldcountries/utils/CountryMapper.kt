package com.noemi.worldcountries.utils

import com.noemi.worldcountries.CountriesQuery
import com.noemi.worldcountries.CountryQuery
import com.noemi.worldcountries.models.Country
import com.noemi.worldcountries.models.DetailedCountry


fun CountryQuery.Country.toDetailedCountry(): DetailedCountry = DetailedCountry(
    code = code,
    name = name,
    emoji = emoji,
    capital = capital ?: "No capital at this time",
    currency = currency ?: "No currency at this time",
    continent = continent.name,
    language = languages.joinToString(", ") { it.name }
)

fun CountriesQuery.Country.toCountry(): Country = Country(
    code = code,
    name = name,
    emoji = emoji,
    capital = capital ?: "No capital at this time"
)