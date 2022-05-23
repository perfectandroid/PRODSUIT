package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.StatusListTicketReportModel
import com.perfect.prodsuit.Repository.StatusListTicketReportRepository

class StatusListTicketReportViewModel  : ViewModel() {

    var statusListTicketReportData: MutableLiveData<StatusListTicketReportModel>? = null
    fun getStatusListReport(context: Context,ReportMode: String?, ID_Branch: String?, strFromdate: String?, strTodate: String?, ID_Product: String?,
                            ID_NextAction: String?, ID_ActionType: String?, ID_Priority: String?, ID_Status: String?, GroupId: String?) : MutableLiveData<StatusListTicketReportModel>? {
        statusListTicketReportData = StatusListTicketReportRepository.getServicesApiCall(context,ReportMode,ID_Branch,strFromdate,strTodate,ID_Product,ID_NextAction,ID_ActionType,ID_Priority,ID_Status,GroupId)
        return statusListTicketReportData
    }
}