package binar.academy.kelompok6.tripie_buyer.data.model.response.notification


import com.google.gson.annotations.SerializedName

data class DataNotification(
    @SerializedName("id")
    val id: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)