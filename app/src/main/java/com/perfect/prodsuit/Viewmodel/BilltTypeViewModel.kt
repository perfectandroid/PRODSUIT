package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.BilltTypeModel
import com.perfect.prodsuit.Repository.BillTypeRepository

class BilltTypeViewModel : ViewModel() {

    var billtTypeData: MutableLiveData<BilltTypeModel>? = null

    fun getBilltType(context: Context,ID_Project : String,ID_Employee : String) : LiveData<BilltTypeModel>? {
        billtTypeData = BillTypeRepository.getServicesApiCall(context,ID_Project,ID_Employee)
        return billtTypeData
    }
}