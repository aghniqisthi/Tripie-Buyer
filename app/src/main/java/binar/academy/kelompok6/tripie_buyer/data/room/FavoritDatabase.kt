package binar.academy.kelompok6.tripie_buyer.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Favorit::class], version = 1, exportSchema = false)
abstract class FavoritDatabase : RoomDatabase() {

    abstract fun favoritDao() : FavoritDAO

//    companion object{
//        private var INSTANCE : FavoritDatabase? = null
//
//        fun getInstance(context : Context):FavoritDatabase? {
//            if (INSTANCE == null){
//                synchronized(FavoritDatabase::class){
//                    INSTANCE = Room.databaseBuilder(context.applicationContext,
//                        FavoritDatabase::class.java,"TripieFavApp.db").build()
//                }
//            }
//            return INSTANCE
//        }
//    }
}