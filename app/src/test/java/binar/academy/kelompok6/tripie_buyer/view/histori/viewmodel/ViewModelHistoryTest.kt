package binar.academy.kelompok6.tripie_buyer.view.histori.viewmodel

import binar.academy.kelompok6.tripie_buyer.data.model.response.history.ResponseHistory
import binar.academy.kelompok6.tripie_buyer.data.network.ApiEndpoint
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import retrofit2.Call

class ViewModelHistoryTest {

    private lateinit var service : ApiEndpoint

    @Before
    fun setUp() {
        service = mockk()
    }

    @Test
    fun getHistoryTest() : Unit = runBlocking {
        val response = mockk<Call<ResponseHistory>>()
        every {
            runBlocking {
                service.getHistory()
            }
        } returns response

        val result = service.getHistory()

        verify {
            runBlocking {
                service.getHistory()
            }
        }
        assertEquals(result, response)
    }
}