package binar.academy.kelompok6.tripie_buyer.data.model.response.googleauth


import com.google.gson.annotations.SerializedName

data class DataGetGoogle(
    @SerializedName("id")
    val id: Int,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Encrypted_Password")
    val encryptedPassword: Any,
    @SerializedName("Role")
    val role: String,
    @SerializedName("Foto")
    val foto: Any,
    @SerializedName("Address")
    val address: Any,
    @SerializedName("Email")
    val email: String,
    @SerializedName("Phone_Number")
    val phoneNumber: Any,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)