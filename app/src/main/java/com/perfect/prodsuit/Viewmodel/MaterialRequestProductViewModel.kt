package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.MaterialRequestProductModel
import com.perfect.prodsuit.Repository.MaterialRequestProductRepository

class MaterialRequestProductViewModel : ViewModel()  {

    var materialRequestProductData: MutableLiveData<MaterialRequestProductModel>? = null

    fun getMaterialRequestProduct(context: Context) : LiveData<MaterialRequestProductModel>? {
        materialRequestProductData = MaterialRequestProductRepository.getServicesApiCall(context)
        return materialRequestProductData
    }
}