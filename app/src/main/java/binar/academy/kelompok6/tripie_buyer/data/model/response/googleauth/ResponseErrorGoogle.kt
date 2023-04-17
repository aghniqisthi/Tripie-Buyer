package binar.academy.kelompok6.tripie_buyer.data.model.response.googleauth


import com.google.gson.annotations.SerializedName

data class ResponseErrorGoogle(
    @SerializedName("error")
    val error: Error
)