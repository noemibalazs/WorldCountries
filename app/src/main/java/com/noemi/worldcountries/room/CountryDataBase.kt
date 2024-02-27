package com.noemi.worldcountries.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.noemi.worldcountries.models.Country

@Database(entities = [Country::class], version = 1, exportSchema = false)
abstract class CountryDataBase : RoomDatabase() {

    abstract fun getCountryDao(): CountryDAO
}