package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.BilltTypeModel
import com.perfect.prodsuit.Repository.BillTypeRepository

class BilltTypeViewModel : ViewModel() {

    var billtTypeData: MutableLiveData<BilltTypeModel>? = null

    fun getBilltType(context: Context) : LiveData<BilltTypeModel>? {
        billtTypeData = BillTypeRepository.getServicesApiCall(context)
        return billtTypeData
    }
}