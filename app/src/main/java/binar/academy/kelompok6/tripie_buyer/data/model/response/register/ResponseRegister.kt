package binar.academy.kelompok6.tripie_buyer.data.model.response.register


import com.google.gson.annotations.SerializedName

data class ResponseRegister(
    @SerializedName("Email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Role")
    val role: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)