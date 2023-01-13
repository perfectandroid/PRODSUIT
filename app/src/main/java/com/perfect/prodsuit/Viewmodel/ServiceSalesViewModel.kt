package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceSalesModel
import com.perfect.prodsuit.Repository.ServiceSalesRepository

class ServiceSalesViewModel: ViewModel()  {

    var serviceSalesData: MutableLiveData<ServiceSalesModel>? = null

    fun getServiceSales(context: Context,ID_Product : String, Customer_Type: String, ID_Customer: String) : LiveData<ServiceSalesModel>? {
        serviceSalesData = ServiceSalesRepository.getServicesApiCall(context,ID_Product,Customer_Type,ID_Customer)
        return serviceSalesData
    }
}