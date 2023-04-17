package binar.academy.kelompok6.tripie_buyer.data.model.response.profile


import com.google.gson.annotations.SerializedName

data class DataUpdateUser(
    @field:SerializedName("Name")
    val name: String?,
    @field:SerializedName("Email")
    val email: String?,
    @field:SerializedName("Encrypted_Password")
    val encryptedPassword: String?,
    @field:SerializedName("Foto")
    val foto: String?,
    @field:SerializedName("Address")
    val address: String?,
    @field:SerializedName("Phone_Number")
    val phoneNumber: String?
)