package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ProductStockModel
import com.perfect.prodsuit.Repository.ProductStockRepository

class ProductStockViewModel : ViewModel() {

    var productStockData: MutableLiveData<ProductStockModel>? = null

    fun getProductStock(context: Context, FK_Stock : String ) : LiveData<ProductStockModel>? {
        productStockData = ProductStockRepository.getServicesApiCall(context,FK_Stock)
        return productStockData
    }
}