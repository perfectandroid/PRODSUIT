package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ChartTypeModel
import com.perfect.prodsuit.Repository.ChartTypeRepository

class ChartTypeViewModel: ViewModel() {

    var chartTypeData: MutableLiveData<ChartTypeModel>? = null

    fun getChartType(context: Context, ReqMode : String,SubMode : String) : LiveData<ChartTypeModel>? {
        chartTypeData = ChartTypeRepository.getServicesApiCall(context,ReqMode,SubMode)
        return chartTypeData
    }
}