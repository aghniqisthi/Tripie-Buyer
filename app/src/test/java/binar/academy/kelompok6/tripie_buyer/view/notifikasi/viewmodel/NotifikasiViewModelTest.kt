package binar.academy.kelompok6.tripie_buyer.view.notifikasi.viewmodel

import binar.academy.kelompok6.tripie_buyer.data.model.response.notification.GetNotificationResponse
import binar.academy.kelompok6.tripie_buyer.data.network.ApiEndpoint
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Call

class NotifikasiViewModelTest {

    private lateinit var service : ApiEndpoint

    @Before
    fun setUp() {
        service = mockk()
    }

    @Test
    fun getNotifikasiTest() : Unit = runBlocking {
        val response = mockk<Call<GetNotificationResponse>>()
        every {
            runBlocking {
                service.getNotification(1)
            }
        } returns response

        val result = service.getNotification(1)

        verify {
            runBlocking {
                service.getNotification(1)
            }
        }
        assertEquals(result, response)
    }
}