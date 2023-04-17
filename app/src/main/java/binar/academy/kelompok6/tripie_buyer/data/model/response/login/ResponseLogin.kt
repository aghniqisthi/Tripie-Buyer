package binar.academy.kelompok6.tripie_buyer.data.model.response.login


import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("id")
    val id: Int,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Email")
    val email: String,
    @SerializedName("Role")
    val role: String,
    @SerializedName("Token")
    val token: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)