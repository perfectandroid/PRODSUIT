package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CRMStagewiseDetailsModel
import com.perfect.prodsuit.Repository.CRMStagewiseDetailsRepository

class CRMStagewiseDetailsViewModel  : ViewModel(){

    var crmStagewiseDetailsData: MutableLiveData<CRMStagewiseDetailsModel>? = null

    fun getCRMStagewiseDetails(context: Context, TransDate : String, DashMode : String, DashType : String) : LiveData<CRMStagewiseDetailsModel>? {
        crmStagewiseDetailsData = CRMStagewiseDetailsRepository.getServicesApiCall(context,TransDate,DashMode,DashType)
        return crmStagewiseDetailsData
    }
}