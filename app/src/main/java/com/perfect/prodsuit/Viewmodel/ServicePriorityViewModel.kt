package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServicePriorityModel
import com.perfect.prodsuit.Repository.ServicePriorityRepository

class ServicePriorityViewModel : ViewModel()  {

    var servicepriorityData: MutableLiveData<ServicePriorityModel>? = null

    fun getServicePriority(context: Context) : LiveData<ServicePriorityModel>? {
        servicepriorityData = ServicePriorityRepository.getServicesApiCall(context)
        return servicepriorityData
    }
}