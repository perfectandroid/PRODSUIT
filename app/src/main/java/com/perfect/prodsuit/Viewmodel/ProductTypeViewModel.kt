package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ProductTypeModel
import com.perfect.prodsuit.Repository.ProductTypeRepository


class ProductTypeViewModel: ViewModel() {

    var productTypeData: MutableLiveData<ProductTypeModel>? = null

    fun getProductType(context: Context) : LiveData<ProductTypeModel>? {
        productTypeData = ProductTypeRepository.getServicesApiCall(context)
        return productTypeData
    }
}