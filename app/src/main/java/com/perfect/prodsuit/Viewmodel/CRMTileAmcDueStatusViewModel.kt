package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CRMTileAmcDueStatusModel
import com.perfect.prodsuit.Repository.CRMTileAmcDueStatusRepository

class CRMTileAmcDueStatusViewModel  : ViewModel() {

    var cRMTileAmcDueStatusData: MutableLiveData<CRMTileAmcDueStatusModel>? = null

    fun getCRMTileAmcDueStatuData(context: Context, TransDate: String, DashMode: String, DashType: String): LiveData<CRMTileAmcDueStatusModel>? {
        cRMTileAmcDueStatusData = CRMTileAmcDueStatusRepository.getServicesApiCall(context, TransDate, DashMode, DashType)
        return cRMTileAmcDueStatusData
    }
}