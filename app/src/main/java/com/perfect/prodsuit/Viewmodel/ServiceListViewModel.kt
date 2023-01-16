package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceListModel
import com.perfect.prodsuit.Repository.ServiceListRepository

class ServiceListViewModel : ViewModel() {

    var serviceListData: MutableLiveData<ServiceListModel>? = null
    fun getServiceList(context: Context) : LiveData<ServiceListModel>? {
        serviceListData = ServiceListRepository.getServicesApiCall(context)
        return serviceListData
    }
}