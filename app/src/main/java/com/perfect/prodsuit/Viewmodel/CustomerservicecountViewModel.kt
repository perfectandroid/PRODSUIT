package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CustomerservicecountModel
import com.perfect.prodsuit.Repository.CustomerservicecountRepository

class CustomerservicecountViewModel : ViewModel() {

    var customerserviceCountData: MutableLiveData<CustomerservicecountModel>? = null
    fun getCustomerserviceCount(
        context: Context,
        ID_Customer: String,
        Customer_Type: String, ID_Product: String,CurrentDate:String
    ) : MutableLiveData<CustomerservicecountModel>? {
        customerserviceCountData = CustomerservicecountRepository.getServicesApiCall(context,ID_Customer,Customer_Type,ID_Product,CurrentDate)
        return customerserviceCountData
    }
}