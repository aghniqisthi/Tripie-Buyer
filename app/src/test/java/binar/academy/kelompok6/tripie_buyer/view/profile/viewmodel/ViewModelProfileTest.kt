package binar.academy.kelompok6.tripie_buyer.view.profile.viewmodel

import binar.academy.kelompok6.tripie_buyer.data.model.response.profile.ResponseUser
import org.junit.Assert.*
import binar.academy.kelompok6.tripie_buyer.data.network.ApiEndpoint
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import org.junit.Before
import org.junit.Test

class ViewModelProfileTest {
    private lateinit var service : ApiEndpoint

    @Before
    fun setUp() {
        service = mockk()
    }

    @Test
    fun getprofileTest() : Unit = runBlocking {
        val response = mockk<Call<ResponseUser>>()
        every {
            runBlocking {
                service.getProfile(1)
            }
        } returns response

        val result = service.getProfile(1)

        verify {
            runBlocking {
                service.getProfile(1)
            }
        }
        assertEquals(result, response)
    }
}