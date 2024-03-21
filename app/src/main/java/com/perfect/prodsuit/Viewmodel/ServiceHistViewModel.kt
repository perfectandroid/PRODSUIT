package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceHistModel
import com.perfect.prodsuit.Repository.ServiceHistoryRepository

class ServiceHistViewModel : ViewModel() {

    var serviceHistData: MutableLiveData<ServiceHistModel>? = null
    fun getServiceHist(context: Context) : MutableLiveData<ServiceHistModel>? {
        serviceHistData = ServiceHistoryRepository.getServicesApiCall(context)
        return serviceHistData
    }
}