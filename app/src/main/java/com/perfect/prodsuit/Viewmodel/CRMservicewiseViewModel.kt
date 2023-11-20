package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CRMservicewiseModel
import com.perfect.prodsuit.Repository.CRMservicewiseRepository

class CRMservicewiseViewModel: ViewModel(){

    var crmservicewiseData: MutableLiveData<CRMservicewiseModel>? = null

    fun getCRMservicewise(context: Context, TransDate : String, DashMode : String, DashType : String) : LiveData<CRMservicewiseModel>? {
        crmservicewiseData = CRMservicewiseRepository.getServicesApiCall(context,TransDate,DashMode,DashType)
        return crmservicewiseData
    }
}