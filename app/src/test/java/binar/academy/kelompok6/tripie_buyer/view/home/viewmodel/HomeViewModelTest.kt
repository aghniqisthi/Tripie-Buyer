package binar.academy.kelompok6.tripie_buyer.view.home.viewmodel

import binar.academy.kelompok6.tripie_buyer.data.model.request.SearchTicketRequest
import binar.academy.kelompok6.tripie_buyer.data.model.response.search.ResponseSearchTicket
import binar.academy.kelompok6.tripie_buyer.data.network.ApiEndpoint
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import retrofit2.Call

class HomeViewModelTest {

    private lateinit var service : ApiEndpoint

    @Before
    fun setUp() {
        service = mockk()
    }

    @Test
    fun getAllAirportTest() : Unit = runBlocking {
        val response = mockk<Call<ResponseSearchTicket>>()
        every {
            runBlocking {
                service.search(SearchTicketRequest(
                    originName = "Ngurah Rai (Bali) International Airport",
                    destinationName = "Soekarno-Hatta International Airport",
                    planeClass = "economy",
                    flightDate = "06-13-2022",
                    totalPassenger = 3
                ))
            }
        } returns response

        val result = service.search(SearchTicketRequest(
            originName = "Ngurah Rai (Bali) International Airport",
            destinationName = "Soekarno-Hatta International Airport",
            planeClass = "economy",
            flightDate = "06-13-2022",
            totalPassenger = 3
        ))

        verify {
            runBlocking {
                service.search(SearchTicketRequest(
                    originName = "Ngurah Rai (Bali) International Airport",
                    destinationName = "Soekarno-Hatta International Airport",
                    planeClass = "economy",
                    flightDate = "06-13-2022",
                    totalPassenger = 3
                ))
            }
        }
        assertEquals(result, response)
    }
}