package binar.academy.kelompok6.tripie_buyer.data.model.response.googleauth


import com.google.gson.annotations.SerializedName

data class ResponseSuccessGoogle(
    @SerializedName("accessToken")
    val accessToken: String
)