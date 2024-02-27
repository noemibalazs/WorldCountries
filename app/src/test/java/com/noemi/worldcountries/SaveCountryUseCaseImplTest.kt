package com.noemi.worldcountries

import com.noemi.worldcountries.models.Country
import com.noemi.worldcountries.room.CountryDAO
import com.noemi.worldcountries.usecase.SaveCountryUseCase
import com.noemi.worldcountries.usecase.SaveCountryUseCaseImpl
import io.kotest.matchers.shouldBe
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
class SaveCountryUseCaseImplTest {

    @Mock
    private lateinit var countryDAO: CountryDAO

    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    private lateinit var useCase: SaveCountryUseCase

    private val code = "HU"
    private val name = "Hungary"
    private val emoji = "emoji"
    private val capital = "Budapest"
    private val country = Country(code, name, emoji, capital)

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = SaveCountryUseCaseImpl(
            countryDAO = countryDAO,
            dispatcher = dispatcher
        )
    }

    @Test
    fun `test save country and should returns Unit`() = runBlocking {
        val job = launch {
            val result = useCase.execute(country)
            result.shouldBe(Unit)
        }

        job.cancelAndJoin()
    }
}