package com.perfect.prodsuit.Viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceFollowUpProductListModel
import com.perfect.prodsuit.Repository.ServiceFollowProductListRepository

class ServiceFollowProductListViewModel : ViewModel() {

    var serviceFollowUpProductListLiveData: MutableLiveData<ServiceFollowUpProductListModel>? = null

    fun getServiceFollowUpProductList(context: Context,ID_Branch : String) : LiveData<ServiceFollowUpProductListModel>? {
        serviceFollowUpProductListLiveData = ServiceFollowProductListRepository.getServicesApiCall(context,ID_Branch)
        return serviceFollowUpProductListLiveData
    }
}