package com.noemi.worldcountries.models

data class DetailedCountry(
    val code: String,
    val name: String,
    val emoji: String,
    val capital: String,
    val currency: String,
    val continent: String,
    val language: String
)