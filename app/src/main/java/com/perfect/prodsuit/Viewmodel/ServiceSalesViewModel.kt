package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceSalesModel
import com.perfect.prodsuit.Repository.ServiceSalesRepository

class ServiceSalesViewModel: ViewModel()  {

    var serviceSalesData: MutableLiveData<ServiceSalesModel>? = null

    fun getServiceSales(context: Context) : LiveData<ServiceSalesModel>? {
        serviceSalesData = ServiceSalesRepository.getServicesApiCall(context)
        return serviceSalesData
    }
}