package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AgendaActionModel
import com.perfect.prodsuit.Repository.AgendaActionRepository

class AgendaActionViewModel : ViewModel() {

    var agendaActionData: MutableLiveData<AgendaActionModel>? = null
    fun getAgendaAction(context: Context) : MutableLiveData<AgendaActionModel>? {
        agendaActionData = AgendaActionRepository.getServicesApiCall(context)
        return agendaActionData
    }
}