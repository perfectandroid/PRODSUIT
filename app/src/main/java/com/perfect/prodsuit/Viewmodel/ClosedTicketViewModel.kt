package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ActionListTicketReportModel
import com.perfect.prodsuit.Model.ClosedTicketModel
import com.perfect.prodsuit.Repository.ActionListTicketReportRepository
import com.perfect.prodsuit.Repository.ClosedTicketRepository

class ClosedTicketViewModel : ViewModel() {

    var closedTicketData: MutableLiveData<ClosedTicketModel>? = null
    fun getclosedTicket(context: Context) : MutableLiveData<ClosedTicketModel>? {
        closedTicketData = ClosedTicketRepository.getServicesApiCall(context)
        return closedTicketData
    }
}