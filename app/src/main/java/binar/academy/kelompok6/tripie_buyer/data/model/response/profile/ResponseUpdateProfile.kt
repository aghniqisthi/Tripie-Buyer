package binar.academy.kelompok6.tripie_buyer.data.model.response.profile


import com.google.gson.annotations.SerializedName

data class ResponseUpdateProfile(
    @field:SerializedName("status")
    val status: String,
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("data")
    val data: List<DataUpdateUser>
)