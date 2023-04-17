package binar.academy.kelompok6.tripie_buyer.data.model.request


import com.google.gson.annotations.SerializedName

data class GoogleAuthRequest(
    @SerializedName("token")
    val token: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String
)