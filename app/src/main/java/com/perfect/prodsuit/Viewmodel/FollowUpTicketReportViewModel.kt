package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.FollowUpTicketReportModel
import com.perfect.prodsuit.Repository.FollowUpTicketReportRepository

class FollowUpTicketReportViewModel : ViewModel()  {

    var followUpTicketTicketReportData: MutableLiveData<FollowUpTicketReportModel>? = null
    fun getFollowUpTicketReport(context: Context,ReportMode: String?, ID_Branch: String?, ID_Employee : String? ,strFromdate: String?, strTodate: String?, ID_Product: String?,
                                ID_NextAction: String?, ID_ActionType: String?, ID_Priority: String?, ID_Status: String?, GroupId: String?) : MutableLiveData<FollowUpTicketReportModel>? {
        followUpTicketTicketReportData = FollowUpTicketReportRepository.getServicesApiCall(context,ReportMode,ID_Branch,ID_Employee,strFromdate,strTodate,ID_Product,ID_NextAction,ID_ActionType,ID_Priority,ID_Status,GroupId)
        return followUpTicketTicketReportData
    }
}