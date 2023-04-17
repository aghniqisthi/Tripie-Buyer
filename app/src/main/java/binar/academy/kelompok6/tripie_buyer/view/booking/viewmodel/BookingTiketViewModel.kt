package binar.academy.kelompok6.tripie_buyer.view.booking.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.academy.kelompok6.tripie_buyer.data.model.request.BookingTicketRequest
import binar.academy.kelompok6.tripie_buyer.data.model.response.bookingticket.ResponseBookingTicket
import binar.academy.kelompok6.tripie_buyer.data.network.ApiEndpoint
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import binar.academy.kelompok6.tripie_buyer.utils.EspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class BookingTiketViewModel @Inject constructor(private val api : ApiEndpoint) : ViewModel(){
    private val postBookingLiveData : MutableLiveData<ApiResponse<ResponseBookingTicket>> = MutableLiveData()

    fun postBookingObserver() : MutableLiveData<ApiResponse<ResponseBookingTicket>> = postBookingLiveData

    fun bookingTicket(bookingTicketRequest: BookingTicketRequest){
        postBookingLiveData.postValue(ApiResponse.Loading())
        EspressoIdlingResource.increment()

        api.bookingTicket(bookingTicketRequest)
            .enqueue(object : Callback<ResponseBookingTicket>{
                override fun onResponse(
                    call: Call<ResponseBookingTicket>,
                    response: Response<ResponseBookingTicket>
                ) {
                    if (response.isSuccessful){
                        val data = response.body()

                        data?.let {
                            postBookingLiveData.postValue(ApiResponse.Success(it))
                        }
                    }else{
                        try {
                            response.errorBody()?.let {
                                val jsonObject = JSONObject(it.string()).getString("message")
                                postBookingLiveData.postValue(ApiResponse.Error(jsonObject))
                            }
                        } catch (e: Exception) {
                            postBookingLiveData.postValue(ApiResponse.Error("${e.message}"))
                        }
                    }

                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<ResponseBookingTicket>, t: Throwable) {
                    postBookingLiveData.postValue(ApiResponse.Error("${t.message}"))
                }

            })
    }
}