package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ClosedTicketModel
import com.perfect.prodsuit.Repository.ClosedTicketRepository

class ClosedTicketViewModel : ViewModel() {

    var closedTicketData: MutableLiveData<ClosedTicketModel>? = null
    fun getclosedTicket(context: Context, Idcudtomerregisterdetails: String?) : MutableLiveData<ClosedTicketModel>? {
        closedTicketData = ClosedTicketRepository.getServicesApiCall(context,Idcudtomerregisterdetails)
        return closedTicketData
    }
}