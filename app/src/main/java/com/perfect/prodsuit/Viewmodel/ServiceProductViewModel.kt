package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceProductModel
import com.perfect.prodsuit.Repository.ServiceProductRepository

class ServiceProductViewModel: ViewModel() {

    var serviceProductData: MutableLiveData<ServiceProductModel>? = null

    fun getServiceProduct(context: Context,ReqMode: String, SubMode: String,Customer_Type: String,ID_Customer: String) : LiveData<ServiceProductModel>? {
        serviceProductData = ServiceProductRepository.getServicesApiCall(context,ReqMode,SubMode,Customer_Type,ID_Customer)
        return serviceProductData
    }
}