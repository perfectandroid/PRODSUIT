package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceListModel
import com.perfect.prodsuit.Repository.ServiceOngoingListRepository

class ServiceOnGoingListViewModel  : ViewModel() {
    var serviceListData: MutableLiveData<ServiceListModel>? = null
    fun getServiceOngoingList(context: Context, SubMode : String, ID_Branch : String, FK_Area : String, ID_Employee : String, strFromDate : String, strToDate : String, strCustomer : String,
                       strMobile : String, strTicketNo : String, strDueDays : String) : LiveData<ServiceListModel>? {
        serviceListData = ServiceOngoingListRepository.getServicesApiCall(context,SubMode,ID_Branch,FK_Area,ID_Employee,strFromDate,strToDate,strCustomer,strMobile,strTicketNo,strDueDays)
        return serviceListData
    }
}