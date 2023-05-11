package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.FinancePlanTypeModel

import com.perfect.prodsuit.Repository.FinancePlanTypeRepository
class FinancePlanTypeViewModel   : ViewModel() {

    var financePlanTypeLiveData: MutableLiveData<FinancePlanTypeModel>? = null

    fun getFinancePlanType(context: Context) : LiveData<FinancePlanTypeModel>? {
        financePlanTypeLiveData = FinancePlanTypeRepository.getServicesApiCall(context)
        return financePlanTypeLiveData
    }
}