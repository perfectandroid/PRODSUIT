package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CRMCountOfWPAModel
import com.perfect.prodsuit.Repository.CRMCountOfWPARepository
import com.perfect.prodsuit.Repository.CRMTop10ProductRepository

class CRMCountOfWPAViewModel : ViewModel(){

    var crmCountOfWPAData: MutableLiveData<CRMCountOfWPAModel>? = null

    fun getCRMCountOfWPA(context: Context, TransDate : String, DashMode : String, DashType : String) : LiveData<CRMCountOfWPAModel>? {
        crmCountOfWPAData = CRMCountOfWPARepository.getServicesApiCall(context,TransDate,DashMode,DashType)
        return crmCountOfWPAData
    }
}