package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceReplacedModeModel
import com.perfect.prodsuit.Repository.ServiceReplacedModeRepository

class ServiceReplacedModeViewModel : ViewModel() {

    var ServiceReplacedModeLiveData: MutableLiveData<ServiceReplacedModeModel>? = null

    fun getServiceReplacedModeViewModel(context: Context, ReplceMode :  String) : LiveData<ServiceReplacedModeModel>? {
        ServiceReplacedModeLiveData = ServiceReplacedModeRepository.getServicesApiCall(context,ReplceMode)
        return ServiceReplacedModeLiveData
    }

}