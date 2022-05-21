package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ActionListTicketReportModel
import com.perfect.prodsuit.Repository.ActionListTicketReportRepository

class ActionListTicketReportViewModel : ViewModel() {

    var actionListTicketReportData: MutableLiveData<ActionListTicketReportModel>? = null
    fun getActionListTicketReport(context: Context,ReportMode: String?, ID_Branch: String?, strFromdate: String?, strTodate: String?, ID_Product: String?,
                                  ID_NextAction: String?, ID_ActionType: String?, ID_Priority: String?, ID_Status: String?, GroupId: String?) : MutableLiveData<ActionListTicketReportModel>? {
        actionListTicketReportData = ActionListTicketReportRepository.getServicesApiCall(context,ReportMode,ID_Branch,strFromdate,strTodate,ID_Product,ID_NextAction,ID_ActionType,ID_Priority,ID_Status,GroupId)
        return actionListTicketReportData
    }
}