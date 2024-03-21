package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.SortAgendaListModel
import com.perfect.prodsuit.Repository.SortAgendaListRepository

class SortAgendaViewModel : ViewModel() {

    var sortagendaLiveData: MutableLiveData<SortAgendaListModel>? = null

    fun getSortAgendalist(context: Context,ID_ActionType : String ,SubMode : String,Id_Agenda : String) : LiveData<SortAgendaListModel>? {
        sortagendaLiveData = SortAgendaListRepository.getServicesApiCall(context,ID_ActionType,SubMode,Id_Agenda)
        return sortagendaLiveData
    }

}