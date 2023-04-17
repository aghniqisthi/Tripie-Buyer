package binar.academy.kelompok6.tripie_buyer.data.room

import androidx.room.*

@Dao
interface FavoritDAO {
    @Query("SELECT * FROM Favorit ORDER BY id DESC")
    suspend fun getData() : List<Favorit>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFav(notes : Favorit)

    @Delete
    suspend fun deleteFav(notes: Favorit)

    @Query("SELECT count(*) FROM Favorit WHERE id =:id")
    suspend fun checkFav(id : Int) : Int

    @Query("SELECT * FROM Favorit WHERE id =:id")
    suspend fun getFav(id : Int) : Favorit
}

