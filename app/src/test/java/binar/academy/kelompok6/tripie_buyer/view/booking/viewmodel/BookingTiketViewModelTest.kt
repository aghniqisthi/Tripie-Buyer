package binar.academy.kelompok6.tripie_buyer.view.booking.viewmodel

import binar.academy.kelompok6.tripie_buyer.data.model.request.BookingTicketRequest
import binar.academy.kelompok6.tripie_buyer.data.model.response.bookingticket.ResponseBookingTicket
import binar.academy.kelompok6.tripie_buyer.data.network.ApiEndpoint
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import retrofit2.Call

class BookingTiketViewModelTest {

    private lateinit var service : ApiEndpoint

    @Before
    fun setUp() {
        service = mockk()
    }

    @Test
    fun bookingTicketTest() : Unit = runBlocking {
        val response = mockk<Call<ResponseBookingTicket>>()
        every {
            runBlocking {
                service.bookingTicket(BookingTicketRequest(
                    userId = 1,
                    scheduleId = 1,
                    originName = "Soekarno-Hatta International Airport",
                    destinationName =  "Ngurah Rai (Bali) International Airport",
                    planeClass =  "economy",
                    totalPassenger = 300,
                    flightType = "One Way Trip",
                    flightDate =  "06-13-2022",
                    flightBackDate = "00-00-0000",
                    departureHour = "07:55",
                    arrivalHour = "07:55",
                    price = 2000000,
                    passengerName = "Budhi",
                    phoneNumber = "081222231321"
                ))
            }
        } returns response

        val result = service.bookingTicket(BookingTicketRequest(
            userId = 1,
            scheduleId = 1,
            originName = "Soekarno-Hatta International Airport",
            destinationName =  "Ngurah Rai (Bali) International Airport",
            planeClass =  "economy",
            totalPassenger = 300,
            flightType = "One Way Trip",
            flightDate =  "06-13-2022",
            flightBackDate = "00-00-0000",
            departureHour = "07:55",
            arrivalHour = "07:55",
            price = 2000000,
            passengerName = "Budhi",
            phoneNumber = "081222231321"
        ))

        verify {
            runBlocking {
                service.bookingTicket(BookingTicketRequest(
                    userId = 1,
                    scheduleId = 1,
                    originName = "Soekarno-Hatta International Airport",
                    destinationName =  "Ngurah Rai (Bali) International Airport",
                    planeClass =  "economy",
                    totalPassenger = 300,
                    flightType = "One Way Trip",
                    flightDate =  "06-13-2022",
                    flightBackDate = "00-00-0000",
                    departureHour = "07:55",
                    arrivalHour = "07:55",
                    price = 2000000,
                    passengerName = "Budhi",
                    phoneNumber = "081222231321"
                ))
            }
        }
        assertEquals(result, response)
    }
}