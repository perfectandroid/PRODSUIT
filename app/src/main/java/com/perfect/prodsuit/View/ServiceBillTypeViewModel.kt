package com.perfect.prodsuit.View

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceBillTypeModel
import com.perfect.prodsuit.Repository.ServiceBillTypeRepository

class ServiceBillTypeViewModel : ViewModel()  {

    var billTypeData: MutableLiveData<ServiceBillTypeModel>? = null
    fun getBillType(context: Context) : LiveData<ServiceBillTypeModel>? {
        billTypeData = ServiceBillTypeRepository.getServicesApiCall(context)
        return billTypeData
    }
}