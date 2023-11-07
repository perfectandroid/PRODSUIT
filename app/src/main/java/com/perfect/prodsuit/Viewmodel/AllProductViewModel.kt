package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AllProductModel
import com.perfect.prodsuit.Repository.AllProductRepository

class AllProductViewModel : ViewModel() {
    var allProductData: MutableLiveData<AllProductModel>? = null

    fun getAllProduct(context: Context,Reqmode : String) : LiveData<AllProductModel>? {
        allProductData = AllProductRepository.getServicesApiCall(context,Reqmode)
        return allProductData
    }
}