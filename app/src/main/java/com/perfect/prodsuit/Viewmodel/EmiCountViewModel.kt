package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.EmiCountModel
import com.perfect.prodsuit.Repository.EmiCountRepository

class EmiCountViewModel  : ViewModel() {

    var emiCountLiveData: MutableLiveData<EmiCountModel>? = null

    fun getEmiCount(context: Context, ID_FinancePlanType : String,AsOnDate : String,ID_Category : String,ID_Area : String,Demand : String) : LiveData<EmiCountModel>? {
        emiCountLiveData = EmiCountRepository.getServicesApiCall(context,ID_FinancePlanType,AsOnDate,ID_Category,ID_Area,Demand)
        return emiCountLiveData
    }
}