package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CustomerListModel
import com.perfect.prodsuit.Repository.CustomerListRepository

class CustomerListViewModel : ViewModel(){

    var customerListData: MutableLiveData<CustomerListModel>? = null
    fun getCustomerList(context: Context, strCustomer : String,ReqMode :String, SubMode : String) : LiveData<CustomerListModel>? {
        customerListData = CustomerListRepository.getServicesApiCall(context,strCustomer,ReqMode,SubMode)
        return customerListData
    }
}