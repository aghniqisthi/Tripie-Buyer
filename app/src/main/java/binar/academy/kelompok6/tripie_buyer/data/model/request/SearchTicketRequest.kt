package binar.academy.kelompok6.tripie_buyer.data.model.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchTicketRequest(
    @SerializedName("origin_name")
    val originName: String?,
    @SerializedName("destination_name")
    val destinationName: String?,
    @SerializedName("plane_class")
    val planeClass: String?,
    @SerializedName("flight_date")
    val flightDate: String?,
    @SerializedName("total_passenger")
    val totalPassenger: Int?
) : Parcelable