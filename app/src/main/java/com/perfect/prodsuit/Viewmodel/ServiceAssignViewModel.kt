package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceAssignModel
import com.perfect.prodsuit.Repository.ServiceAssignRepository

class ServiceAssignViewModel  : ViewModel(){

    var serviceAssignLiveData: MutableLiveData<ServiceAssignModel>? = null
    fun getServiceAssign(context: Context) : LiveData<ServiceAssignModel>? {
        serviceAssignLiveData = ServiceAssignRepository.getServicesApiCall(context)
        return serviceAssignLiveData
    }
}