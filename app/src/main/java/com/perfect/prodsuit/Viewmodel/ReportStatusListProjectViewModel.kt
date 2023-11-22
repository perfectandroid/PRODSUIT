package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ProjectStatuslistReportModel
import com.perfect.prodsuit.Repository.ReportStatuslistProjectRepository

class ReportStatusListProjectViewModel : ViewModel() {

    var reportSitevisitProjectData: MutableLiveData<ProjectStatuslistReportModel>? = null

    fun getReportstatuslistProject(context: Context,RegMode : String,Fromdate : String,Todate : String,IdLead : String) : LiveData<ProjectStatuslistReportModel>? {
        reportSitevisitProjectData = ReportStatuslistProjectRepository.getServicesApiCall(context,RegMode,Fromdate,Todate,IdLead)
        return reportSitevisitProjectData
    }
}