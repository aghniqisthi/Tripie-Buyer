package binar.academy.kelompok6.tripie_buyer.data.model.response.profile


import com.google.gson.annotations.SerializedName

data class ResponseErrorUpdateProfile(
    @SerializedName("status")
    val status: String,
    @SerializedName("error")
    val error: Error
)