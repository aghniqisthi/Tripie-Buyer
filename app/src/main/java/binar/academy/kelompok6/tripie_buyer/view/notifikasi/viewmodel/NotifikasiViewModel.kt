package binar.academy.kelompok6.tripie_buyer.view.notifikasi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.academy.kelompok6.tripie_buyer.data.model.response.notification.GetNotificationResponse
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
class NotifikasiViewModel @Inject constructor(private val api : ApiEndpoint) : ViewModel() {
    private var notifikasiLiveData : MutableLiveData<ApiResponse<GetNotificationResponse>> = MutableLiveData()

    fun getNotifikasiObserver() : MutableLiveData<ApiResponse<GetNotificationResponse>> = notifikasiLiveData

    fun getNotifikasi(id : Int){
        notifikasiLiveData.postValue(ApiResponse.Loading())
        EspressoIdlingResource.increment()

        api.getNotification(id).enqueue(object : Callback<GetNotificationResponse>{
            override fun onResponse(
                call: Call<GetNotificationResponse>,
                response: Response<GetNotificationResponse>
            ) {
                if(response.isSuccessful){
                    val data = response.body()

                    data?.let {
                        notifikasiLiveData.postValue(ApiResponse.Success(it))
                    }
                }
                else {
                    try {
                        response.errorBody()?.let {
                            val jsonObject = JSONObject(it.string()).getString("message")
                            notifikasiLiveData.postValue(ApiResponse.Error(jsonObject))
                        }
                    } catch (e: Exception) {
                        notifikasiLiveData.postValue(ApiResponse.Error("${e.message}"))
                    }
                }
            }

            override fun onFailure(call: Call<GetNotificationResponse>, t: Throwable) {
                notifikasiLiveData.postValue(ApiResponse.Error("${t.message}"))
            }

        })
    }
}