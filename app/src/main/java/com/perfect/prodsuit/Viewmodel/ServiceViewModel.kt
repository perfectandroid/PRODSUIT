package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceModel
import com.perfect.prodsuit.Repository.ServiceRepository

class ServiceViewModel   : ViewModel(){

    var serviceData: MutableLiveData<ServiceModel>? = null

    fun getServices(context: Context,ReqMode : String, SubMode: String, ID_Product: String,ID_Category : String) : LiveData<ServiceModel>? {
        serviceData = ServiceRepository.getServicesApiCall(context,ReqMode,SubMode,ID_Product,ID_Category)
        return serviceData
    }
}