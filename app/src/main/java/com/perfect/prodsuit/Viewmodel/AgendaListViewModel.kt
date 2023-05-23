package com.perfect.prodsuit.Viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AgendaListModel
import com.perfect.prodsuit.Repository.AgendaListRepository

class AgendaListViewModel : ViewModel() {
    var agendalistLiveData: MutableLiveData<AgendaListModel>? = null
    fun getAgendalist(context: Context,ReqMode : String,SubMode : String,ID_FinancePlanType : String,AsOnDate : String,ID_Category : String,ID_Area : String,Demand : String
    ):LiveData<AgendaListModel>?
    {
        Log.i("responseww","AsOnDate view model=  "+AsOnDate)
        agendalistLiveData=AgendaListRepository.getAgendaApiCall(context,ReqMode,SubMode,ID_FinancePlanType,AsOnDate,ID_Category,ID_Area,Demand)
        return agendalistLiveData
    }
}