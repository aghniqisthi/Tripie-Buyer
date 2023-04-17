package binar.academy.kelompok6.tripie_buyer.view.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.academy.kelompok6.tripie_buyer.data.model.PlaneClass

class PlaneClassViewModel : ViewModel() {

    val planeClassList : MutableLiveData<List<PlaneClass>> = MutableLiveData()

    private val listPlaneClass = arrayListOf(
        PlaneClass("economy"),
        PlaneClass("business"),
        PlaneClass("first class")
    )

    fun getPlaneClass(){
        planeClassList.value = listPlaneClass
    }
}