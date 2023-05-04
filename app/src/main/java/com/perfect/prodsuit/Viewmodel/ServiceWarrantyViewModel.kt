package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceWarrantyModel
import com.perfect.prodsuit.Repository.ServiceWarrantyRepository

class ServiceWarrantyViewModel  : ViewModel(){

    var serviceWarrantyData: MutableLiveData<ServiceWarrantyModel>? = null

    fun getServiceWarranty(context: Context,ID_Product : String,Customer_Type : String,ID_Customer : String,ID_CustomerWiseProductDetails : String) : LiveData<ServiceWarrantyModel>? {
        serviceWarrantyData = ServiceWarrantyRepository.getServicesApiCall(context,ID_Product,Customer_Type,ID_Customer,ID_CustomerWiseProductDetails)
        return serviceWarrantyData
    }
}