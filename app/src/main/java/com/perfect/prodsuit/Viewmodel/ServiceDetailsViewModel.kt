package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceDetailsModel
import com.perfect.prodsuit.Repository.ServiceDetailsRepository

class ServiceDetailsViewModel : ViewModel() {

    var servicedetailsData: MutableLiveData<ServiceDetailsModel>? = null

    fun getServiceDetails(context: Context,FK_Product: String, NameCriteria: String) : LiveData<ServiceDetailsModel>? {
        servicedetailsData = ServiceDetailsRepository.getServicesApiCall(context,FK_Product,NameCriteria)
        return servicedetailsData
    }
}