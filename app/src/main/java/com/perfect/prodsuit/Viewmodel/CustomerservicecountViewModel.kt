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
        fkCust: String,
        fkOthercustomer: String, Prodid: String
    ) : MutableLiveData<CustomerservicecountModel>? {
        customerserviceCountData = CustomerservicecountRepository.getServicesApiCall(context,fkCust,fkOthercustomer,Prodid)
        return customerserviceCountData
    }
}