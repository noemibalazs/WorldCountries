package com.noemi.worldcountries.di

import android.content.Context
import androidx.room.Room
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.noemi.worldcountries.room.CountryDAO
import com.noemi.worldcountries.room.CountryDataBase
import com.noemi.worldcountries.network.ApolloCountryClient
import com.noemi.worldcountries.network.CountryClient
import com.noemi.worldcountries.usecase.*
import com.noemi.worldcountries.utils.BASE_URL
import com.noemi.worldcountries.utils.COUNTRY_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }


    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(interceptor).build()

    @Provides
    @Singleton
    fun providesApolloClient(client: OkHttpClient): ApolloClient =
        ApolloClient.Builder().serverUrl(BASE_URL).okHttpClient(client).build()


    @Provides
    @Singleton
    fun providesApolloCountryClient(apolloClient: ApolloClient): CountryClient =
        ApolloCountryClient(apolloClient)

    @Provides
    @Singleton
    fun providesCountryDataBase(@ApplicationContext context: Context): CountryDataBase =
        Room.databaseBuilder(context, CountryDataBase::class.java, COUNTRY_DB).build()

    @Provides
    @Singleton
    fun providesCountryDao(countryDataBase: CountryDataBase): CountryDAO = countryDataBase.getCountryDao()

    @Singleton
    @Provides
    fun provideDispatchers(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun providesGetCountriesUseCase(countryClient: CountryClient, dispatcher: CoroutineDispatcher): GetCountriesUseCase =
        GetCountriesUseCase(countryClient, dispatcher)

    @Provides
    @Singleton
    fun providesGetCountryByNameUseCase(countryDAO: CountryDAO, dispatcher: CoroutineDispatcher): GetCountryByNameUseCase =
        GetCountryByNameUseCase(countryDAO, dispatcher)


    @Provides
    @Singleton
    fun providesGetCountryUseCase(countryClient: CountryClient, dispatcher: CoroutineDispatcher): GetCountryUseCase =
        GetCountryUseCase(countryClient, dispatcher)

    @Provides
    @Singleton
    fun providesSaveCountryUseCase(countryDAO: CountryDAO, dispatcher: CoroutineDispatcher): SaveCountryUseCase =
        SaveCountryUseCase(countryDAO, dispatcher)

    @Provides
    @Singleton
    fun providesGetFavoriteCountriesUseCase(countryDAO: CountryDAO, dispatcher: CoroutineDispatcher): GetFavoriteCountriesUseCase =
        GetFavoriteCountriesUseCase(countryDAO, dispatcher)
}