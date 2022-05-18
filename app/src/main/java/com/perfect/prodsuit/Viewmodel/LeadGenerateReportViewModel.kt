package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadGenerateReportModel
import com.perfect.prodsuit.Repository.LeadGenerateReportRepository

class LeadGenerateReportViewModel  : ViewModel() {
    var leadGenerateReportLiveData: MutableLiveData<LeadGenerateReportModel>? = null
    fun getLeadGenerateReport(context: Context,strFromdate : String,strTodate : String,strDashboardTypeId : String) : LiveData<LeadGenerateReportModel>? {
        leadGenerateReportLiveData = LeadGenerateReportRepository.getServicesApiCall(context,strFromdate,strTodate,strDashboardTypeId)
        return leadGenerateReportLiveData
    }
}