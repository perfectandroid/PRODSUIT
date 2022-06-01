package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CustomerSearchModel
import com.perfect.prodsuit.Repository.CustomerSearchRepository

class CustomerSearchViewModel  : ViewModel(){

    var customerLiveData: MutableLiveData<CustomerSearchModel>? = null

    fun getCustomer(context: Context,strCustomer : String,SubModeSearch : String) : LiveData<CustomerSearchModel>? {
        customerLiveData = CustomerSearchRepository.getServicesApiCall(context,strCustomer,SubModeSearch)
        return customerLiveData
    }

}