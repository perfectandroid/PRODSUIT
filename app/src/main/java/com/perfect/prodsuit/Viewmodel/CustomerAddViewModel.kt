package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CustomerAddModel
import com.perfect.prodsuit.Model.CustomerSearchModel
import com.perfect.prodsuit.Reprository.CustomerAddRepository
import com.perfect.prodsuit.Reprository.CustomerSearchRepository


class CustomerAddViewModel   : ViewModel() {
    var customerAddData: MutableLiveData<CustomerAddModel>? = null

    fun addCustomerApi(context: Context) : MutableLiveData<CustomerAddModel>? {
        customerAddData = CustomerAddRepository.getServicesApiCall(context)
        return customerAddData
    }
}