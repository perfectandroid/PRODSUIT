package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CatModel
import com.perfect.prodsuit.Repository.CatRepository

class CatViewModel : ViewModel() {

    var catData: MutableLiveData<CatModel>? = null

    fun getCat(context: Context) : LiveData<CatModel>? {
        catData = CatRepository.getServicesApiCall(context)
        return catData
    }
}