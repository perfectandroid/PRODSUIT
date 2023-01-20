package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceRoleModel
import com.perfect.prodsuit.Repository.ServiceRoleRepository

class ServiceRoleViewModel: ViewModel()  {

    var serviceRoleData: MutableLiveData<ServiceRoleModel>? = null

    fun getServiceRole(context: Context) : LiveData<ServiceRoleModel>? {
        serviceRoleData = ServiceRoleRepository.getServicesApiCall(context)
        return serviceRoleData
    }
}