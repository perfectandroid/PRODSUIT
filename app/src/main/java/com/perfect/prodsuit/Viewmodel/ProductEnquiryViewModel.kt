package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ProductEnquiryModel
import com.perfect.prodsuit.Repository.ProductEnquiryRepository

class ProductEnquiryViewModel : ViewModel() {

    var productEnquiryData: MutableLiveData<ProductEnquiryModel>? = null

    fun getProductEnquiry(context: Context, ReqMode : String, ID_Category : String, ID_Product : String, isOffersOnly : String) : LiveData<ProductEnquiryModel>? {
        productEnquiryData = ProductEnquiryRepository.getServicesApiCall(context,ReqMode,ID_Category,ID_Product,isOffersOnly)
        return productEnquiryData
    }

}