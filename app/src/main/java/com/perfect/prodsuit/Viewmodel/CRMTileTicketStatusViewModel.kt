package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CRMTileTicketStatusModel
import com.perfect.prodsuit.Repository.CRMTileTicketStatusRepository

class CRMTileTicketStatusViewModel  : ViewModel(){

    var cRMTileTicketStatusData: MutableLiveData<CRMTileTicketStatusModel>? = null

    fun getCRMTileTicketStatusData(context: Context, TransDate : String, DashMode : String, DashType : String) : LiveData<CRMTileTicketStatusModel>? {
        cRMTileTicketStatusData = CRMTileTicketStatusRepository.getServicesApiCall(context,TransDate,DashMode,DashType)
        return cRMTileTicketStatusData
    }
}