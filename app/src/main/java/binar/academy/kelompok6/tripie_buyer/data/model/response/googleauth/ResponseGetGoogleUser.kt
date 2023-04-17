package binar.academy.kelompok6.tripie_buyer.data.model.response.googleauth


import com.google.gson.annotations.SerializedName

data class ResponseGetGoogleUser(
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: DataGetGoogle
)