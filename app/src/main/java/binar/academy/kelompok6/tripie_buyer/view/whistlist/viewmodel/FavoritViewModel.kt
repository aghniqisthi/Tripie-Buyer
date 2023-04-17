package binar.academy.kelompok6.tripie_buyer.view.whistlist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import binar.academy.kelompok6.tripie_buyer.data.room.Favorit
import binar.academy.kelompok6.tripie_buyer.data.room.FavoritRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritViewModel @Inject constructor (private val repository: FavoritRepository) : ViewModel() {

    private var allFav : MutableLiveData<List<Favorit>> = MutableLiveData()

    suspend fun cekFav(id : Int) = repository.cekFav(id)
    fun getAllFavObserver() : MutableLiveData<List<Favorit>> = allFav

    fun getAllFav() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllFav()
            allFav.postValue(repository.getAllFav())
        }
    }

    fun insertFav(fav: Favorit){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFav(fav)
        }
    }

    fun deleteFav(fav: Favorit){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFav(fav)
        }
    }

}