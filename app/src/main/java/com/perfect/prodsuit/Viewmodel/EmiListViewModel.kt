package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.EmiListModel
import com.perfect.prodsuit.Repository.EmiListRepository

class EmiListViewModel  : ViewModel() {
    var emiListLiveData: MutableLiveData<EmiListModel>? = null

    fun getEmiList(context: Context,ReqMode : String,SubMode : String,ID_FinancePlanType : String,AsOnDate : String,ID_Category : String,ID_Area : String,Demand : String) : LiveData<EmiListModel>? {
        emiListLiveData = EmiListRepository.getEmiList(context,ReqMode,SubMode,ID_FinancePlanType,AsOnDate,ID_Category,ID_Area,Demand)
        return emiListLiveData
    }
}