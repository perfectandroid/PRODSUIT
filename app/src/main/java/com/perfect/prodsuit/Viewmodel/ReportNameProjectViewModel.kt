package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ReportNameModel
import com.perfect.prodsuit.Model.ReportNameProjectModel
import com.perfect.prodsuit.Repository.ReportNameProjectRepository
import com.perfect.prodsuit.Repository.ReportNameRepository

class ReportNameProjectViewModel : ViewModel() {

    var reportNameProjectData: MutableLiveData<ReportNameProjectModel>? = null

    fun getReportNameProject(context: Context,SubMode : String) : LiveData<ReportNameProjectModel>? {
        reportNameProjectData = ReportNameProjectRepository.getServicesApiCall(context,SubMode)
        return reportNameProjectData
    }
}