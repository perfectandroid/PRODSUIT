package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ProductInfoDetailsModel
import com.perfect.prodsuit.Repository.ProductInfoDetailsRepository

class ProductInfoDetailsViewModel : ViewModel(){

    var ProductInfoDetailsData: MutableLiveData<ProductInfoDetailsModel>? = null

    fun getProductInfoDetails(context: Context, SubMode: String, ID_ProductDelivery: String, TransMode: String) : LiveData<ProductInfoDetailsModel>? {
        ProductInfoDetailsData = ProductInfoDetailsRepository.getServicesApiCall(context,SubMode,ID_ProductDelivery,TransMode)
        return ProductInfoDetailsData


     }
}