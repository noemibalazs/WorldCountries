package com.noemi.worldcountries.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.noemi.worldcountries.models.Country
import com.noemi.worldcountries.utils.TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: Country)

    @Query("SELECT * FROM $TABLE_NAME WHERE name = :name")
    suspend fun getCountryByName(name: String): Country?

    @Query("SELECT * FROM $TABLE_NAME")
    fun observeCountries(): Flow<List<Country>>
}