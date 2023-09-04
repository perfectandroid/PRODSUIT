package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ProductStockTransferModel
import com.perfect.prodsuit.Repository.ProductStockTransferRepository

class ProductStockTransferViewModel  : ViewModel() {

    var productStockTransferData: MutableLiveData<ProductStockTransferModel>? = null

    fun getProductStockTransfer(context: Context, ReqMode : String , Critrea1 : String, Critrea2 : String, TransMode : String) : LiveData<ProductStockTransferModel>? {
        productStockTransferData = ProductStockTransferRepository.getServicesApiCall(context,ReqMode,Critrea1,Critrea2,TransMode)
        return productStockTransferData
    }

}