package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ProductDetailModel
import com.perfect.prodsuit.Model.ProductStatusModel
import com.perfect.prodsuit.Reprository.ProductDetailRepository
import com.perfect.prodsuit.Reprository.ProductStatusRepository

class ProductStatusViewModel : ViewModel() {

    var productstatusData: MutableLiveData<ProductStatusModel>? = null
    fun getProductStatus(context: Context) : LiveData<ProductStatusModel>? {
        productstatusData = ProductStatusRepository.getServicesApiCall(context)
        return productstatusData
    }
}