package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.StatusListTicketReportModel
import com.perfect.prodsuit.Repository.StatusListTicketReportRepository

class StatusListTicketReportViewModel  : ViewModel() {

    var statusListTicketReportData: MutableLiveData<StatusListTicketReportModel>? = null
    fun getStatusListReport(context: Context) : MutableLiveData<StatusListTicketReportModel>? {
        statusListTicketReportData = StatusListTicketReportRepository.getServicesApiCall(context)
        return statusListTicketReportData
    }
}