package binar.academy.kelompok6.tripie_buyer.view.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.academy.kelompok6.tripie_buyer.data.model.request.SearchTicketRequest
import binar.academy.kelompok6.tripie_buyer.data.model.response.search.ResponseSearchTicket
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
class HomeViewModel @Inject constructor(private val api : ApiEndpoint) : ViewModel() {
    private var liveDataSearch : MutableLiveData<ApiResponse<ResponseSearchTicket>> = MutableLiveData()

    fun ambilLiveDataSearch() : MutableLiveData<ApiResponse<ResponseSearchTicket>> = liveDataSearch

    fun searchData(request : SearchTicketRequest){
        liveDataSearch.postValue(ApiResponse.Loading())
        EspressoIdlingResource.increment()
        api.search(request).enqueue(object : Callback<ResponseSearchTicket> {
            override fun onResponse(call: Call<ResponseSearchTicket>, response: Response<ResponseSearchTicket>) {
                if(response.isSuccessful){
                    val data = response.body()

                    data?.let {
                        liveDataSearch.postValue(ApiResponse.Success(it))
                    }
                }
                else {
                    try {
                        response.errorBody()?.let {
                            val jsonObject = JSONObject(it.string()).getString("message")
                            liveDataSearch.postValue(ApiResponse.Error(jsonObject))
                        }
                    } catch (e: Exception) {
                        liveDataSearch.postValue(ApiResponse.Error("${e.message}"))
                    }
                }
            }

            override fun onFailure(call: Call<ResponseSearchTicket>, t: Throwable) {
                liveDataSearch.postValue(ApiResponse.Error("${t.message}"))
            }

        })
    }

}