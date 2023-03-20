package com.perfect.prodsuit.Viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.*
import com.perfect.prodsuit.Repository.*

class ServiceFollowUpMoreServiceViewModel : ViewModel() {

    var serviceFollowUpMoreServiceLiveData: MutableLiveData<ServiceFollowUpMoreServiceModel>? = null

    fun getServiceFollowUpMoreService(context: Context,customer_service_register:String,ID_Branch : String , ID_Employee : String) : LiveData<ServiceFollowUpMoreServiceModel>? {
        Log.v("fsfsfds","branch4 "+ID_Branch)
        serviceFollowUpMoreServiceLiveData = ServiceFollowUpMoreServiceRepository.getServicesApiCall(context,customer_service_register,ID_Branch,ID_Employee)
        return serviceFollowUpMoreServiceLiveData
    }

}