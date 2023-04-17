package binar.academy.kelompok6.tripie_buyer.data.model.response.bookingticket


import com.google.gson.annotations.SerializedName

data class ResponseBookingTicket(
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data : DataBookingTicket?
)