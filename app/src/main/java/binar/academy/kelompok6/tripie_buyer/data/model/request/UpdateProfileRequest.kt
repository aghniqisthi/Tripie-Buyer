@file:Suppress("unused")

package binar.academy.kelompok6.tripie_buyer.data.model.request


import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @SerializedName("Address")
    val address: String?,
    @SerializedName("Email")
    val email: String?,
    @SerializedName("Encrypted_Password")
    val encryptedPassword: String?,
    @SerializedName("Foto")
    val foto: String?,
    @SerializedName("Name")
    val name: String?,
    @SerializedName("Phone_Number")
    val phoneNumber: String?
)