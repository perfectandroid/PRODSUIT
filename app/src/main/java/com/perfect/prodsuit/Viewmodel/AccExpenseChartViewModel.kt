package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AccExpenseChartModel
import com.perfect.prodsuit.Repository.AccExpenseChartRepository

class AccExpenseChartViewModel : ViewModel() {

    var accExpenseChartData: MutableLiveData<AccExpenseChartModel>? = null

    fun getAccExpenseChart(context: Context, TransDate : String, DashMode : String, DashType : String) : LiveData<AccExpenseChartModel>? {
        accExpenseChartData = AccExpenseChartRepository.getServicesApiCall(context,TransDate,DashMode,DashType)
        return accExpenseChartData
    }
}