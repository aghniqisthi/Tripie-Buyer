package binar.academy.kelompok6.tripie_buyer.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "user")


class SharedPref (private val context: Context) {

    private val token = stringPreferencesKey("token")
    private val status = booleanPreferencesKey("status")
    private val idUser = stringPreferencesKey("idUser")
    private val username = stringPreferencesKey("username")
    private val totalPassenger = intPreferencesKey("tpass")
    private val typeFlight = stringPreferencesKey("typeflight")

    private val originCode = stringPreferencesKey("ogCode")
    private val destCode = stringPreferencesKey("destCode")
    private val originCity = stringPreferencesKey("ogCity")
    private val destCity = stringPreferencesKey("destCity")

    suspend fun saveToken(tokens : String,idUsers : String, usernames : String){
        context.dataStore.edit {
            it[token] = tokens
            it[idUser] = idUsers
            it[username] = usernames
        }
    }

    suspend fun saveSearch(tpass : Int, tflight : String){
        context.dataStore.edit {
            it[totalPassenger] = tpass
            it[typeFlight] = tflight
        }
    }

    suspend fun saveDataOriginAirport(codes : String, city: String){
        context.dataStore.edit {
            it[originCode] = codes
            it[originCity] = city
        }
    }

    suspend fun saveDataDestAirport(codes : String, city: String){
        context.dataStore.edit {
            it[destCode] = codes
            it[destCity] = city
        }
    }

    suspend fun saveStatus(statuss:Boolean){
        context.dataStore.edit {
            it[status] = statuss
        }
    }

    val getToken : Flow<String> = context.dataStore.data
        .map {
            it[token] ?: "Undefined"
        }

    val getIdUser : Flow<String> = context.dataStore.data
        .map {
            it[idUser] ?: "Undefined"
        }

    val getUsername : Flow<String> = context.dataStore.data
        .map {
            it[username] ?: "Undefined"
        }

    val getStatus : Flow<Boolean> = context.dataStore.data
        .map {
            it[status] ?: false
        }

    val getTotalPassenger : Flow<Int> = context.dataStore.data
        .map {
            it[totalPassenger] ?: 0
        }

    val getOriginCode : Flow<String> = context.dataStore.data
        .map {
            it[originCode] ?: "Undefined"
        }

    val getOriginCity : Flow<String> = context.dataStore.data
        .map {
            it[originCity] ?: "Undefined"
        }

    val getDestCode : Flow<String> = context.dataStore.data
        .map {
            it[destCode] ?: "Undefined"
        }

    val getDestCity : Flow<String> = context.dataStore.data
        .map {
            it[destCity] ?: "Undefined"
        }

    suspend fun removeToken(){
        context.dataStore.edit {
            it.remove(token)
            it.remove(idUser)
            it.remove(username)
            it.remove(totalPassenger)
            it.remove(typeFlight)
            it.remove(originCode)
            it.remove(originCity)
            it.remove(destCode)
            it.remove(destCity)
        }
    }
}