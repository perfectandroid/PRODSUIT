package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CRMTileDashBoardDetailsModel
import com.perfect.prodsuit.Repository.CRMTileDashBoardDetailsRepository

class CRMTileDashBoardDetailsViewModel : ViewModel(){

    var cRMTileDashBoardDetailsData: MutableLiveData<CRMTileDashBoardDetailsModel>? = null

    fun getCRMTileDashBoardDetails(context: Context, TransDate : String, DashMode : String, DashType : String) : LiveData<CRMTileDashBoardDetailsModel>? {
        cRMTileDashBoardDetailsData = CRMTileDashBoardDetailsRepository.getServicesApiCall(context,TransDate,DashMode,DashType)
        return cRMTileDashBoardDetailsData
    }
}