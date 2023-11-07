package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.MainSubProductModel
import com.perfect.prodsuit.Repository.MainSubProductRepository

class MainSubProductViewModel : ViewModel() {

    var mainSubProductData: MutableLiveData<MainSubProductModel>? = null

    fun getMainSubProductData(context: Context) : LiveData<MainSubProductModel>? {
        mainSubProductData = MainSubProductRepository.getServicesApiCall(context)
        return mainSubProductData
    }
}