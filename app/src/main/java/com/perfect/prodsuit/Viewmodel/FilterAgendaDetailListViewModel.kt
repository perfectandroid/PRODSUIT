package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ActivityListModel
import com.perfect.prodsuit.Model.FilterAgendaDettailListModel
import com.perfect.prodsuit.Repository.ActivityListRepository
import com.perfect.prodsuit.Repository.FilterAgendaListRepository

class FilterAgendaDetailListViewModel : ViewModel() {

    var filteragendalistLiveData: MutableLiveData<FilterAgendaDettailListModel>? = null

    fun getFilterAgendalist(context: Context,ID_ActionType : String ,SubMode : String,Id_Agenda : String) : LiveData<FilterAgendaDettailListModel>? {
        filteragendalistLiveData = FilterAgendaListRepository.getServicesApiCall(context,ID_ActionType,SubMode,Id_Agenda)
        return filteragendalistLiveData
    }

}