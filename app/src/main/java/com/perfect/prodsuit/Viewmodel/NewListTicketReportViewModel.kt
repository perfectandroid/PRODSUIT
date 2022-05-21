package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.NewListTicketReportModel
import com.perfect.prodsuit.Repository.NewListTicketReportRepository

class NewListTicketReportViewModel : ViewModel() {

    var newListTicketReportData: MutableLiveData<NewListTicketReportModel>? = null
    fun getNewListTicketReport(context: Context) : MutableLiveData<NewListTicketReportModel>? {
        newListTicketReportData = NewListTicketReportRepository.getServicesApiCall(context)
        return newListTicketReportData
    }
}