package com.noemi.worldcountries

import com.noemi.worldcountries.room.CountryDAO
import com.noemi.worldcountries.usecase.GetCountryByNameUseCase
import com.noemi.worldcountries.usecase.GetCountryByNameUseCaseImpl
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class GetCountryByNameUseCaseImplTest {

    @Mock
    private lateinit var countryDAO: CountryDAO

    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    private lateinit var useCase: GetCountryByNameUseCase

    private val name = "Hungary"

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = GetCountryByNameUseCaseImpl(
            countryDAO = countryDAO,
            dispatcher = dispatcher
        )
    }

    @Test
    fun `test get country by name and should be not null`() = runBlocking {
        val job = launch {
            val result = useCase.execute(name)
            result?.shouldNotBe(null)
        }

        job.cancelAndJoin()
    }


    @Test
    fun `test get country by name and should be null`() = runBlocking {
        val job = launch {
            val result = useCase.execute("")
            result?.shouldBe(null)
        }

        job.cancelAndJoin()
    }
}