package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CRMcomplaintwiseModel
import com.perfect.prodsuit.Repository.CRMcomplaintwiseRepository

class CRMcomplaintwiseViewModel  : ViewModel(){

    var crmcomplaintwiseData: MutableLiveData<CRMcomplaintwiseModel>? = null

    fun getCRMcomplaintwise(context: Context, TransDate : String, DashMode : String, DashType : String) : LiveData<CRMcomplaintwiseModel>? {
        crmcomplaintwiseData = CRMcomplaintwiseRepository.getServicesApiCall(context,TransDate,DashMode,DashType)
        return crmcomplaintwiseData
    }
}