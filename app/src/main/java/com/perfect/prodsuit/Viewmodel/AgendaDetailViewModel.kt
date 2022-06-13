package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AgendaDetailModel
import com.perfect.prodsuit.Repository.AgendaDetailRepository

class AgendaDetailViewModel : ViewModel() {

    var agendaDetailData: MutableLiveData<AgendaDetailModel>? = null
    fun getAgendaDetail(context: Context,ID_ActionType : String ,SubMode : String,Id_Agenda : String) : MutableLiveData<AgendaDetailModel>? {
        agendaDetailData = AgendaDetailRepository.getServicesApiCall(context,ID_ActionType,SubMode,Id_Agenda)
        return agendaDetailData
    }
}