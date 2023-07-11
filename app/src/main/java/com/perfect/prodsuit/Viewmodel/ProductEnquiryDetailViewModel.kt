package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ProductEnquiryDetailModel
import com.perfect.prodsuit.Repository.ProductEnquiryDetailRepository

class ProductEnquiryDetailViewModel  : ViewModel() {

    var productEnquiryDetailData: MutableLiveData<ProductEnquiryDetailModel>? = null

    fun getProductEnquiryDetail(context: Context) : LiveData<ProductEnquiryDetailModel>? {
        productEnquiryDetailData = ProductEnquiryDetailRepository.getServicesApiCall(context)
        return productEnquiryDetailData
    }
}