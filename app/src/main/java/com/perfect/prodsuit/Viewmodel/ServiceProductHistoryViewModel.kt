package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceProductHistoryModel
import com.perfect.prodsuit.Repository.ServiceProductHistoryRepository

class ServiceProductHistoryViewModel : ViewModel() {

    var serviceProductHistData: MutableLiveData<ServiceProductHistoryModel>? = null

    fun getServiceProductHistory(
        context: Context,
        ID_Category: String,
        ID_Product: String,
        Customer_Type: String,
        ID_Customer: String
    ) : LiveData<ServiceProductHistoryModel>? {
        serviceProductHistData = ServiceProductHistoryRepository.getServicesApiCall(context,ID_Category,ID_Product,Customer_Type,ID_Customer)
        return serviceProductHistData
    }
}