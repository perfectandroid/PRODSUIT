package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceAssignModel
import com.perfect.prodsuit.Repository.ServiceAssignRepository

class ServiceAssignViewModel  : ViewModel(){

    var serviceAssignLiveData: MutableLiveData<ServiceAssignModel>? = null
    fun getServiceAssign(context: Context,ReqMode : String,ID_CustomerServiceRegister : String,strAssignees : String,strVisitDate : String,strVisitTime : String,ID_Priority : String,strRemark : String) : LiveData<ServiceAssignModel>? {
        serviceAssignLiveData = ServiceAssignRepository.getServicesApiCall(context,ReqMode,ID_CustomerServiceRegister,strAssignees,strVisitDate,strVisitTime,ID_Priority,strRemark)
        return serviceAssignLiveData
    }
}