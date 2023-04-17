package binar.academy.kelompok6.tripie_buyer.data.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Favorit(
    @PrimaryKey(autoGenerate = true) var id:Int,
    var idUser : String,
    val airportCode: String?,
    val airportName: String?,
    val city: String?,
    val foto: String?,
    val description: String?) : Parcelable
