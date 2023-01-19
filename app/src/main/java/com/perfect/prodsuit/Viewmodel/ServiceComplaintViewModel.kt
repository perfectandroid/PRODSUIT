package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceComplaintModel
import com.perfect.prodsuit.Repository.ServiceComplaintRepository

class ServiceComplaintViewModel : ViewModel(){

    var serviceComplaintData: MutableLiveData<ServiceComplaintModel>? = null

    fun getserviceComplaintData(context: Context, ReqMode : String, SubMode: String, ID_Product: String) : LiveData<ServiceComplaintModel>? {
        serviceComplaintData = ServiceComplaintRepository.getServicesApiCall(context,ReqMode,SubMode,ID_Product)
        return serviceComplaintData
    }
}