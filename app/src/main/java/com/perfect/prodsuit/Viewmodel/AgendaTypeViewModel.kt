package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AgendaTypeModel
import com.perfect.prodsuit.Repository.AgendaTypeRepository

class AgendaTypeViewModel : ViewModel() {

    var agendaTypeData: MutableLiveData<AgendaTypeModel>? = null
    fun getAgendaType(context: Context) : MutableLiveData<AgendaTypeModel>? {
        agendaTypeData = AgendaTypeRepository.getServicesApiCall(context)
        return agendaTypeData
    }
}