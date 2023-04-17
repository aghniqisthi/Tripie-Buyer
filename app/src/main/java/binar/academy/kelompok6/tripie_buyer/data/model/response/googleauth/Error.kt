package binar.academy.kelompok6.tripie_buyer.data.model.response.googleauth


import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("name")
    val name: String,
    @SerializedName("message")
    val message: String
)