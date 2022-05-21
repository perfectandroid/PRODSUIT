package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ActionListTicketReportModel
import com.perfect.prodsuit.Repository.ActionListTicketReportRepository

class ActionListTicketReportViewModel : ViewModel() {

    var actionListTicketReportData: MutableLiveData<ActionListTicketReportModel>? = null
    fun getActionListTicketReport(context: Context) : MutableLiveData<ActionListTicketReportModel>? {
        actionListTicketReportData = ActionListTicketReportRepository.getServicesApiCall(context)
        return actionListTicketReportData
    }
}