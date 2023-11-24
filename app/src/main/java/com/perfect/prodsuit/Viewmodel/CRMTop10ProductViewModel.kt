package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CRMTop10ProductModel
import com.perfect.prodsuit.Repository.CRMTop10ProductRepository

class CRMTop10ProductViewModel  : ViewModel(){

    var crmTop10ProductData: MutableLiveData<CRMTop10ProductModel>? = null

    fun getCRMTop10Products(context: Context, TransDate : String, DashMode : String, DashType : String) : LiveData<CRMTop10ProductModel>? {
        crmTop10ProductData = CRMTop10ProductRepository.getServicesApiCall(context,TransDate,DashMode,DashType)
        return crmTop10ProductData
    }
}