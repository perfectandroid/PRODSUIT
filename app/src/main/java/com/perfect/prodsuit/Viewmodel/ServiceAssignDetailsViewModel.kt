package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceAssignDetailsModel
import com.perfect.prodsuit.Repository.ServiceAssignDetailsRepository

class ServiceAssignDetailsViewModel  : ViewModel()  {

    var serviceAssignDetailsData: MutableLiveData<ServiceAssignDetailsModel>? = null

    fun getServiceAssignDetail(
        context: Context,
        ReqMode: String,
        ID_CustomerServiceRegister: String,
        FK_CustomerserviceregisterProductDetails: String,
        TicketDate: String?,
    ) : LiveData<ServiceAssignDetailsModel>? {
        serviceAssignDetailsData = ServiceAssignDetailsRepository.getServicesApiCall(context,ReqMode,ID_CustomerServiceRegister,FK_CustomerserviceregisterProductDetails,TicketDate)
        return serviceAssignDetailsData
    }
}