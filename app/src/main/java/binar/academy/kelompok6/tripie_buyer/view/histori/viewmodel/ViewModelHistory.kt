package binar.academy.kelompok6.tripie_buyer.view.histori.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.academy.kelompok6.tripie_buyer.data.model.response.history.ResponseHistory
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
class ViewModelHistory @Inject constructor (private val api : ApiEndpoint): ViewModel(){
    private var liveDataHistory : MutableLiveData<ApiResponse<ResponseHistory>> = MutableLiveData()

    fun getLiveDataHistory() : MutableLiveData<ApiResponse<ResponseHistory>> = liveDataHistory

    fun getHistory(){
        liveDataHistory.postValue(ApiResponse.Loading())
        EspressoIdlingResource.increment()
        api.getHistory().enqueue(object : Callback<ResponseHistory>{
            override fun onResponse(call: Call<ResponseHistory>, response: Response<ResponseHistory>) {
                if(response.isSuccessful){
                    val data = response.body()

                    data?.let {
                        liveDataHistory.postValue(ApiResponse.Success(it))
                    }
                }else{
                    try {
                        response.errorBody()?.let {
                            val jsonObject = JSONObject(it.string()).getString("message")
                            liveDataHistory.postValue(ApiResponse.Error(jsonObject))
                        }
                    } catch (e: Exception) {
                        liveDataHistory.postValue(ApiResponse.Error("${e.message}"))
                    }
                }
            }

            override fun onFailure(call: Call<ResponseHistory>, t: Throwable) {
                liveDataHistory.postValue(ApiResponse.Error("${t.message}"))
            }

        })
    }
}
