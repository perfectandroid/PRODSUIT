package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ProductEnquiryDetailModel
import com.perfect.prodsuit.Repository.ProductEnquiryDetailRepository

class ProductEnquiryDetailViewModel  : ViewModel() {

    var productEnquiryDetailData: MutableLiveData<ProductEnquiryDetailModel>? = null

    fun getProductEnquiryDetail(context: Context,ID_Category : String,ID_Product : String) : LiveData<ProductEnquiryDetailModel>? {
        productEnquiryDetailData = ProductEnquiryDetailRepository.getServicesApiCall(context,ID_Category,ID_Product)
        return productEnquiryDetailData
    }
}