package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceStatusDashModel
import com.perfect.prodsuit.Repository.ServiceStatusDashRepository

class ServiceStatusDashViewModel : ViewModel() {

    var servicestatusdashData: MutableLiveData<ServiceStatusDashModel>? = null
    fun getServiceStatusDashboard(context: Context) : LiveData<ServiceStatusDashModel>? {
        servicestatusdashData = ServiceStatusDashRepository.getServicesApiCall(context)
        return servicestatusdashData
    }
}