package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ProductWiseComplaintModel
import com.perfect.prodsuit.Repository.ProductWiseComplaintRepository

class ProductWiseComplaintViewModel : ViewModel() {

    var productwisecomplaintLiveData: MutableLiveData<ProductWiseComplaintModel>? = null

    fun getProductWiseComplaint(context: Context,ID_Product: String, ID_Category: String) : LiveData<ProductWiseComplaintModel>? {
        productwisecomplaintLiveData = ProductWiseComplaintRepository.getServicesApiCall(context,ID_Product,ID_Category)
        return productwisecomplaintLiveData
    }

}