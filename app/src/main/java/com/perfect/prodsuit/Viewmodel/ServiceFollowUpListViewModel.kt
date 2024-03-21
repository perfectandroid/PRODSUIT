package com.perfect.prodsuit.Viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceFollowUpModel
import com.perfect.prodsuit.Repository.ServiceFollowUpRepository

class ServiceFollowUpListViewModel : ViewModel() {

    var serviceFollowUplistLiveData: MutableLiveData<ServiceFollowUpModel>? = null

    fun getServiceFollowUplist(context: Context,ID_Branch : String , ID_Employee : String) : LiveData<ServiceFollowUpModel>? {
        Log.v("fsfsfds","branch4 "+ID_Branch)
        serviceFollowUplistLiveData = ServiceFollowUpRepository.getServicesApiCall(context,ID_Branch,ID_Employee)
        return serviceFollowUplistLiveData
    }

}