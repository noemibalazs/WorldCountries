package com.noemi.worldcountries.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.noemi.worldcountries.utils.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Country(
    @PrimaryKey
    val code: String,
    val name: String,
    val emoji: String,
    val capital: String
)