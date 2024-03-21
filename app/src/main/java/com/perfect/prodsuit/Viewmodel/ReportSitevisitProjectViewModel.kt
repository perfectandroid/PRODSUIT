package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ProjectsitevisitReportModel
import com.perfect.prodsuit.Repository.ReportSieVisitProjectRepository

class ReportSitevisitProjectViewModel : ViewModel() {

    var reportSitevisitProjectData: MutableLiveData<ProjectsitevisitReportModel>? = null

    fun getReportsitevisitProject(context: Context,RegMode : String,Fromdate : String,Todate : String,IdLead : String) : LiveData<ProjectsitevisitReportModel>? {
        reportSitevisitProjectData = ReportSieVisitProjectRepository.getServicesApiCall(context,RegMode,Fromdate,Todate,IdLead)
        return reportSitevisitProjectData
    }
}