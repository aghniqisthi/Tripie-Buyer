@file:Suppress("unused")

package binar.academy.kelompok6.tripie_buyer.data.room

import javax.inject.Inject

class FavoritRepository @Inject constructor(private val favDAO: FavoritDAO){

    suspend fun getAllFav() : List<Favorit> = favDAO.getData()

    suspend fun cekFav(id : Int) = favDAO.checkFav(id)

    suspend fun getFav(id : Int) = favDAO.getFav(id)

    suspend fun insertFav(favorit: Favorit) = favDAO.insertFav(favorit)

    suspend fun deleteFav(favorit: Favorit) = favDAO.deleteFav(favorit)
}