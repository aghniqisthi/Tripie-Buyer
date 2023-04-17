@file:Suppress("unused")

package binar.academy.kelompok6.tripie_buyer.data.model.response.airport


import com.google.gson.annotations.SerializedName

data class DataAirport(
    @SerializedName("airport")
    val airport: List<Airport>
)