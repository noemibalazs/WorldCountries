package com.noemi.worldcountries

import com.noemi.worldcountries.models.Country
import com.noemi.worldcountries.room.CountryDAO
import com.noemi.worldcountries.usecase.GetFavoriteCountriesUseCase
import com.noemi.worldcountries.usecase.GetFavoriteCountriesUseCaseImpl
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class GetFavoriteCountriesUseCaseImplTest {

    @Mock
    private lateinit var countryDAO: CountryDAO

    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    private lateinit var useCase: GetFavoriteCountriesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = GetFavoriteCountriesUseCaseImpl(
            dispatcher = dispatcher,
            countryDAO = countryDAO
        )
    }

    @Test
    fun `test get favorite countries and should not be empty list`() = runBlocking {

        val job = launch {
            val results = useCase.execute()
            results.shouldNotBe(emptyList<Country>())
        }

        job.cancelAndJoin()
    }
}