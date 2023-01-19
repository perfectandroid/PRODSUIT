package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceCountModel
import com.perfect.prodsuit.Repository.ServiceCountRepository

class ServiceCountViewModel : ViewModel() {

    var serviceCountData: MutableLiveData<ServiceCountModel>? = null

    fun getServiceCount(context: Context,ID_Branch : String,FK_Area : String,ID_Employee : String,strFromDate : String,strToDate : String,strCustomer : String,
                        strMobile : String,strTicketNo : String,strDueDays : String) : LiveData<ServiceCountModel>? {
        serviceCountData = ServiceCountRepository.getServicesApiCall(context,ID_Branch,FK_Area,ID_Employee,strFromDate,strToDate,strCustomer,strMobile,strTicketNo,strDueDays)
        return serviceCountData
    }
}