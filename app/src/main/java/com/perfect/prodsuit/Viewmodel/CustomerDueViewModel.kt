package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CustomerDueModel
import com.perfect.prodsuit.Repository.CustomerDueRepository

class CustomerDueViewModel: ViewModel()  {

    var customerDueData: MutableLiveData<CustomerDueModel>? = null

    fun getCustomerDue(context: Context,ID_Customer: String) : LiveData<CustomerDueModel>? {
        customerDueData = CustomerDueRepository.getServicesApiCall(context,ID_Customer)
        return customerDueData
    }
}