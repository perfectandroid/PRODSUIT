package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AssignedTicketListModel
import com.perfect.prodsuit.Repository.AssignedTicketListRepository

class AssignedTicketListViewModel : ViewModel() {

    var assignedTicketListData: MutableLiveData<AssignedTicketListModel>? = null
    fun getAssignedTickets(context: Context, FK_Emp: String, date: String) : MutableLiveData<AssignedTicketListModel>? {
        assignedTicketListData = AssignedTicketListRepository.getServicesApiCall(context,FK_Emp,date)
        return assignedTicketListData
    }
}