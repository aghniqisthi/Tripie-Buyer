package binar.academy.kelompok6.tripie_buyer.data.model.response.notification


import com.google.gson.annotations.SerializedName

data class GetNotificationResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: List<DataNotification>
)