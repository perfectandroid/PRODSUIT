package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AccSupplierChartModel
import com.perfect.prodsuit.Repository.AccSupplierChartRepository

class AccSupplierChartViewModel : ViewModel() {

    var accSupplierChartData: MutableLiveData<AccSupplierChartModel>? = null

    fun getAccSupplierChart(context: Context, TransDate : String, DashMode : String, DashType : String) : LiveData<AccSupplierChartModel>? {
        accSupplierChartData = AccSupplierChartRepository.getServicesApiCall(context,TransDate,DashMode,DashType)
        return accSupplierChartData
    }
}