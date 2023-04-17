package binar.academy.kelompok6.tripie_buyer.data.model.response.airport


import com.google.gson.annotations.SerializedName

data class Airport(
    @SerializedName("id")
    val id: Int,
    @SerializedName("Airport_Code")
    val airportCode: String?,
    @SerializedName("Airport_Name")
    val airportName: String?,
    @SerializedName("City")
    val city: String?,
    @SerializedName("Foto")
    val foto: String?,
    @SerializedName("Description")
    val description: String?,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)