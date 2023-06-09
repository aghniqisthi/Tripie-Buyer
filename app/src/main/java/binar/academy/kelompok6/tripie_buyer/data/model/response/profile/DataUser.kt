package binar.academy.kelompok6.tripie_buyer.data.model.response.profile


import com.google.gson.annotations.SerializedName

data class DataUser(
    @SerializedName("id")
    val id: Int,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Encrypted_Password")
    val encryptedPassword: String?,
    @SerializedName("Role")
    val role: String,
    @SerializedName("Foto")
    val foto: String?,
    @SerializedName("Address")
    val address: String?,
    @SerializedName("Email")
    val email: String,
    @SerializedName("Phone_Number")
    val phoneNumber: String?,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)