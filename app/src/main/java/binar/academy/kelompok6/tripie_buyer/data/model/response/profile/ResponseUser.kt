package binar.academy.kelompok6.tripie_buyer.data.model.response.profile


import com.google.gson.annotations.SerializedName

data class ResponseUser(
    @SerializedName("status")
    val status: String,
    @SerializedName("messsage")
    val messsage: String,
    @SerializedName("data")
    val data: DataUser
)