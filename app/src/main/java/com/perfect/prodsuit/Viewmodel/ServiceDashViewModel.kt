package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceDashModel
import com.perfect.prodsuit.Repository.ServiceDashRepository

class ServiceDashViewModel : ViewModel() {

    var servicedashData: MutableLiveData<ServiceDashModel>? = null
    fun getServiceDashboard(context: Context) : LiveData<ServiceDashModel>? {
        servicedashData = ServiceDashRepository.getServicesApiCall(context)
        return servicedashData
    }
}