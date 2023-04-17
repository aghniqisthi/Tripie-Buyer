package binar.academy.kelompok6.tripie_buyer.data.model.response.history


import com.google.gson.annotations.SerializedName

data class DataHistory(
    @SerializedName("Booking")
    val booking: List<Booking>
)