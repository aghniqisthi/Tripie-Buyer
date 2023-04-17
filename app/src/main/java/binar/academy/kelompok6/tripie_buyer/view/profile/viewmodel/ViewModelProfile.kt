package binar.academy.kelompok6.tripie_buyer.view.profile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.academy.kelompok6.tripie_buyer.data.model.response.profile.ResponseErrorUpdateProfile
import binar.academy.kelompok6.tripie_buyer.data.model.response.profile.ResponseUpdateProfile
import binar.academy.kelompok6.tripie_buyer.data.model.response.profile.ResponseUser
import binar.academy.kelompok6.tripie_buyer.data.network.ApiEndpoint
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelProfile @Inject constructor(private val api : ApiEndpoint) : ViewModel() {
    private var profile : MutableLiveData<ApiResponse<ResponseUser>> = MutableLiveData()
    private var updateProfile : MutableLiveData<ApiResponse<ResponseUpdateProfile>> = MutableLiveData()

    fun getLiveDataProfile() : MutableLiveData<ApiResponse<ResponseUser>> = profile
    fun updateLiveDataProfile() : MutableLiveData<ApiResponse<ResponseUpdateProfile>> = updateProfile

    fun getProfile(id : Int){
        api.getProfile(id).enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                if(response.isSuccessful){
                    val data = response.body()

                    data?.let {
                        profile.postValue(ApiResponse.Success(it))
                    }
                }
                else {
                    try {
                        response.errorBody()?.let {
                            val jsonObject = JSONObject(it.string()).getString("message")
                            profile.postValue(ApiResponse.Error(jsonObject))
                        }
                    } catch (e: Exception) {
                        profile.postValue(ApiResponse.Error("${e.message}"))
                    }
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                profile.postValue(ApiResponse.Error("${t.message}"))
            }

        })
    }

    fun updateProfile(id : Int, Name : RequestBody, Email : RequestBody, Encrypted_Password : RequestBody, Foto : MultipartBody.Part, Address : RequestBody, Phone_Number : RequestBody){
        api.updateProfile(id, Name, Email, Encrypted_Password, Foto, Address, Phone_Number)
            .enqueue(object : Callback<ResponseUpdateProfile> {
                override fun onResponse(call: Call<ResponseUpdateProfile>, response: Response<ResponseUpdateProfile>) {
                    if(response.isSuccessful){
                        val data = response.body()

                        data?.let {
                            updateProfile.postValue(ApiResponse.Success(it))
                        }
                    }
                    else {
                        try {
                            response.errorBody()?.let {
                                val gson = GsonBuilder().create()
                                val jsonObject = gson.fromJson(it.string(), ResponseErrorUpdateProfile::class.java)
                                updateProfile.postValue(ApiResponse.Error(jsonObject.error.message))
                            }
                        } catch (e: Exception) {
                            updateProfile.postValue(ApiResponse.Error("${e.message}"))
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseUpdateProfile>, t: Throwable) {
                    updateProfile.postValue(ApiResponse.Error(t.message.toString()))
                }

            })
    }

}