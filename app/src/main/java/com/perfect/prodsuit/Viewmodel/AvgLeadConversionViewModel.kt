package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AverageLeadConversionModel
import com.perfect.prodsuit.Repository.AvergLeadConvrsnRepository

class AvgLeadConversionViewModel : ViewModel() {

    var avgleadconvrsnCountData: MutableLiveData<AverageLeadConversionModel>? = null

    fun getAvgLeadConvrsn(context: Context) : LiveData<AverageLeadConversionModel>? {
        avgleadconvrsnCountData = AvergLeadConvrsnRepository.getServicesApiCall(context)
        return avgleadconvrsnCountData
    }
}