package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CRMSLAStatusModel
import com.perfect.prodsuit.Repository.CRMSLAStatusRepository

class CRMSLAStatusViewModel   : ViewModel(){

    var crmSLAStatusData: MutableLiveData<CRMSLAStatusModel>? = null

    fun getCRMSLAStatusData(context: Context, TransDate : String, DashMode : String, DashType : String) : LiveData<CRMSLAStatusModel>? {
        crmSLAStatusData = CRMSLAStatusRepository.getServicesApiCall(context,TransDate,DashMode,DashType)
        return crmSLAStatusData
    }
}