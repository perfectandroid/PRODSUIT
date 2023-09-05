package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.MaterialProductModel
import com.perfect.prodsuit.Repository.MaterialProductRepository

class MaterialUsageProductViewModel: ViewModel()  {

    var MaterialProductData: MutableLiveData<MaterialProductModel>? = null

    fun getMaterialProduct(context: Context) : LiveData<MaterialProductModel>? {
        MaterialProductData = MaterialProductRepository.getServicesApiCall(context)
        return MaterialProductData
    }

}