package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ProductDetailModel
import com.perfect.prodsuit.Repository.ProductDetailRepository

class ProductDetailViewModel : ViewModel() {

    var productdetailData: MutableLiveData<ProductDetailModel>? = null

    fun getProductDetail(context: Context) : LiveData<ProductDetailModel>? {
        productdetailData = ProductDetailRepository.getServicesApiCall(context)
        return productdetailData
    }

}