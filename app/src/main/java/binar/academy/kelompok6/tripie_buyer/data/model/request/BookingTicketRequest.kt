package binar.academy.kelompok6.tripie_buyer.data.model.request


import com.google.gson.annotations.SerializedName

data class BookingTicketRequest(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("schedule_id")
    val scheduleId: Int,
    @SerializedName("origin_name")
    val originName: String,
    @SerializedName("destination_name")
    val destinationName: String,
    @SerializedName("plane_class")
    val planeClass: String,
    @SerializedName("total_passenger")
    val totalPassenger: Int,
    @SerializedName("flight_type")
    val flightType: String,
    @SerializedName("flight_date")
    val flightDate: String,
    @SerializedName("flight_back_date")
    val flightBackDate: String?,
    @SerializedName("departure_hour")
    val departureHour: String,
    @SerializedName("arrival_hour")
    val arrivalHour: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("passenger_name")
    val passengerName: String,
    @SerializedName("phone_number")
    val phoneNumber: String
)