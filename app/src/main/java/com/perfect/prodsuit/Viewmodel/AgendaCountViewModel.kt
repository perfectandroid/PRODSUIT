package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AgendaCountModel
import com.perfect.prodsuit.Repository.AgendaCountRepository

class AgendaCountViewModel : ViewModel() {

    var agendaCountData: MutableLiveData<AgendaCountModel>? = null
    fun getAgendaCount(context: Context,ID_Employee: String) : MutableLiveData<AgendaCountModel>? {
        agendaCountData = AgendaCountRepository.getServicesApiCall(context,ID_Employee)
        return agendaCountData
    }
}