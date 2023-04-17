package binar.academy.kelompok6.tripie_buyer.data.model.response.history


import com.google.gson.annotations.SerializedName

data class ResponseHistory(
    @SerializedName("data")
    val data: DataHistory
)