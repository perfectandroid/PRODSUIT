package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.FollowUpTicketReportModel
import com.perfect.prodsuit.Repository.FollowUpTicketReportRepository

class FollowUpTicketReportViewModel : ViewModel()  {

    var followUpTicketTicketReportData: MutableLiveData<FollowUpTicketReportModel>? = null
    fun getFollowUpTicketReport(context: Context) : MutableLiveData<FollowUpTicketReportModel>? {
        followUpTicketTicketReportData = FollowUpTicketReportRepository.getServicesApiCall(context)
        return followUpTicketTicketReportData
    }
}