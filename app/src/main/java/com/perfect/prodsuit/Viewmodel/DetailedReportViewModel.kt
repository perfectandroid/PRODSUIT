package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.DetailedReportModel
import com.perfect.prodsuit.Repository.DetailedReportRepository

class DetailedReportViewModel : ViewModel() {

    var detailedReportData: MutableLiveData<DetailedReportModel>? = null
    fun getDetailedReport(
        context: Context,
        ReportMode: String?,
        ID_Branch: String?,
        strFromdate: String?,
        strTodate: String?,
        ID_Product: String?,
        ID_NextAction: String?,
        ID_ActionType: String?,
        ID_Priority: String?,
        ID_Status: String?,
        GroupId: String?,
        ID_AssignedEmployee: String?,
        ID_CollectedBy: String?,
        ID_Category: String?
    ) : MutableLiveData<DetailedReportModel>? {
        detailedReportData = DetailedReportRepository.getServicesApiCall(context,ReportMode,
            ID_Branch,strFromdate,strTodate,ID_Product,ID_NextAction,ID_ActionType,ID_Priority,ID_Status,GroupId,ID_AssignedEmployee,ID_CollectedBy,ID_Category)
        return detailedReportData
    }
}