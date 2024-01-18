package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceTimeLineModel
import com.perfect.prodsuit.Repository.ServiceTimeLineRepository


class ServiceTimeLineViewModel: ViewModel()  {
    var serviceTimeLineData: MutableLiveData<ServiceTimeLineModel>? = null
    fun getServiceTimeLine(context: Context, ID_Master: String, TransMode : String) : LiveData<ServiceTimeLineModel>? {
        serviceTimeLineData = ServiceTimeLineRepository.getServicesApiCall(context,ID_Master,TransMode)
        return serviceTimeLineData
    }
}