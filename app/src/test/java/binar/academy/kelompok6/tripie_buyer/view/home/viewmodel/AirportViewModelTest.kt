package binar.academy.kelompok6.tripie_buyer.view.home.viewmodel

import binar.academy.kelompok6.tripie_buyer.data.model.response.airport.AirportResponse
import binar.academy.kelompok6.tripie_buyer.data.network.ApiEndpoint
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import retrofit2.Call

class AirportViewModelTest {

    private lateinit var service : ApiEndpoint

    @Before
    fun setUp() {
        service = mockk()
    }

    @Test
    fun getAllAirportTest() : Unit = runBlocking {
        val response = mockk<Call<AirportResponse>>()
        every {
            runBlocking {
                service.getAirport()
            }
        } returns response

        val result = service.getAirport()

        verify {
            runBlocking {
                service.getAirport()
            }
        }
        assertEquals(result, response)
    }
}