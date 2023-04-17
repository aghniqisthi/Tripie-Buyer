package binar.academy.kelompok6.tripie_buyer.view.authentication.viewmodel

import binar.academy.kelompok6.tripie_buyer.data.model.request.LoginRequest
import binar.academy.kelompok6.tripie_buyer.data.model.request.RegisterRequest
import binar.academy.kelompok6.tripie_buyer.data.model.response.login.ResponseLogin
import binar.academy.kelompok6.tripie_buyer.data.model.response.register.ResponseRegister
import binar.academy.kelompok6.tripie_buyer.data.network.ApiEndpoint
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import retrofit2.Call

class AuthenticationViewModelTest {

    private lateinit var service : ApiEndpoint

    @Before
    fun setUp() {
        service = mockk()
    }

    @Test
    fun loginUserTest() : Unit = runBlocking {
        val response = mockk<Call<ResponseLogin>>()
        every {
            runBlocking {
                service.login(LoginRequest("admin@tripie.com", "admin"))
            }
        } returns response

        val result = service.login(LoginRequest("admin@tripie.com", "admin"))

        verify {
            runBlocking {
                service.login(LoginRequest("admin@tripie.com", "admin"))
            }
        }
        assertEquals(result, response)
    }

    @Test
    fun registerUserTest() : Unit = runBlocking {
        val response = mockk<Call<ResponseRegister>>()
        every {
            runBlocking {
                service.register(RegisterRequest("admin", "admin@tripie.com", "admin"))
            }
        } returns response

        val result = service.register(RegisterRequest("admin","admin@tripie.com", "admin"))

        verify {
            runBlocking {
                service.register(RegisterRequest("admin", "admin@tripie.com", "admin"))
            }
        }
        assertEquals(result, response)
    }
}