package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.PriorityWiseReportModel
import com.perfect.prodsuit.Repository.PriorityWiseReportRepository

class PriorityWiseReportViewModel : ViewModel() {

    var priorityWiseReportLiveData: MutableLiveData<PriorityWiseReportModel>? = null
    fun getPriorityWiseReport(context: Context, strFromdate : String, strTodate : String, strDashboardTypeId : String) : LiveData<PriorityWiseReportModel>? {
        priorityWiseReportLiveData = PriorityWiseReportRepository.getServicesApiCall(context,strFromdate,strTodate,strDashboardTypeId)
        return priorityWiseReportLiveData
    }
}