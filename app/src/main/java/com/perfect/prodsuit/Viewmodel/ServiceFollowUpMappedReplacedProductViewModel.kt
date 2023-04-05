package com.perfect.prodsuit.Viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.*
import com.perfect.prodsuit.Repository.*

class ServiceFollowUpMappedReplacedProductViewModel : ViewModel() {

    var serviceFollowUpMappedReplacedProductLiveData: MutableLiveData<ServiceFollowUpMappedReplacedProductModel>? = null

    fun getServiceFollowUpMappedService(context: Context,customer_service_register:String,ID_Branch : String , ID_Employee : String) : LiveData<ServiceFollowUpMappedReplacedProductModel>? {
        Log.v("fsfsfds","branch4 "+ID_Branch)
        serviceFollowUpMappedReplacedProductLiveData = ServiceFollowUpMappedReplacedProductRepository.getServicesApiCall(context,customer_service_register,ID_Branch,ID_Employee)
        return serviceFollowUpMappedReplacedProductLiveData
    }

}