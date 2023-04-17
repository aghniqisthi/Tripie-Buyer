@file:Suppress("unused")

package binar.academy.kelompok6.tripie_buyer.di

import android.content.Context
import androidx.room.Room
import binar.academy.kelompok6.tripie_buyer.data.network.ApiEndpoint
import binar.academy.kelompok6.tripie_buyer.data.room.FavoritDatabase
import binar.academy.kelompok6.tripie_buyer.utils.Constant.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private  val logging : HttpLoggingInterceptor
        get(){
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            return httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit) : ApiEndpoint =
        retrofit.create(ApiEndpoint::class.java)

    // For room database
    @Provides
    @Singleton
    fun provideFavoritDatabase(@ApplicationContext context : Context) =
        Room.databaseBuilder(
            context,
            FavoritDatabase::class.java,
            "favorit.db"
        ).build()

    @Provides
    @Singleton
    fun provideFavoritDao(db : FavoritDatabase) = db.favoritDao()
}