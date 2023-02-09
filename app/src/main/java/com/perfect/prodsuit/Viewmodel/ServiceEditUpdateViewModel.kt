package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceEditUpdateModel
import com.perfect.prodsuit.Repository.ServiceEditUpadateRepository

class ServiceEditUpdateViewModel : ViewModel()  {

    var serviceEditUpdateData: MutableLiveData<ServiceEditUpdateModel>? = null
    fun getServiceUpdate(context: Context, ReqMode : String,ID_CustomerServiceRegister : String,strVisitdate  : String,ID_Priority : String,ID_AttendedBy : String,ID_Status : String) : LiveData<ServiceEditUpdateModel>? {
        serviceEditUpdateData = ServiceEditUpadateRepository.getServicesApiCall(context,ReqMode,ID_CustomerServiceRegister,strVisitdate,ID_Priority,ID_AttendedBy,ID_Status)
        return serviceEditUpdateData
    }
}