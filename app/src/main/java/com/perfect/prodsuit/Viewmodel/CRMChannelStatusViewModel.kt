package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CRMChannelStatusModel
import com.perfect.prodsuit.Repository.CRMChannelStatusRepository

class CRMChannelStatusViewModel : ViewModel(){

    var crmChannelStatusData: MutableLiveData<CRMChannelStatusModel>? = null

    fun getCRMChannelStatusData(context: Context, TransDate : String, DashMode : String, DashType : String) : LiveData<CRMChannelStatusModel>? {
        crmChannelStatusData = CRMChannelStatusRepository.getServicesApiCall(context,TransDate,DashMode,DashType)
        return crmChannelStatusData
    }
}