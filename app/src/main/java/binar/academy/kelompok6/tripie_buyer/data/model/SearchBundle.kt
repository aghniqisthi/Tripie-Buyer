package binar.academy.kelompok6.tripie_buyer.data.model

import android.os.Parcelable
import binar.academy.kelompok6.tripie_buyer.data.model.request.SearchTicketRequest
import binar.academy.kelompok6.tripie_buyer.data.model.response.search.ResponseSearchTicket
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchBundle(
    val dataRequest : SearchTicketRequest,
    val dataResponse : ResponseSearchTicket,
    val flight_back_date : String,
    val flight_type : String
) : Parcelable
