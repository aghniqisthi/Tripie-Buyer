package binar.academy.kelompok6.tripie_buyer.data.model.response.search


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseSearchTicket(
    @SerializedName("data")
    val data : List<DataSearch>
) : Parcelable