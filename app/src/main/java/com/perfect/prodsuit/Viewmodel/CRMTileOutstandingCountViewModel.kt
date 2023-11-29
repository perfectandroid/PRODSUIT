package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CRMTileOutstandingCountModel
import com.perfect.prodsuit.Repository.CRMTileOutstandingCountRepository


class CRMTileOutstandingCountViewModel : ViewModel(){

    var cRMTileOutstandingCountData: MutableLiveData<CRMTileOutstandingCountModel>? = null

    fun getCRMTileOutstandingCountData(context: Context, TransDate : String, DashMode : String, DashType : String) : LiveData<CRMTileOutstandingCountModel>? {
        cRMTileOutstandingCountData = CRMTileOutstandingCountRepository.getServicesApiCall(context,TransDate,DashMode,DashType)
        return cRMTileOutstandingCountData
    }
}