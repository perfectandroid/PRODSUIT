package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceMediaModel
import com.perfect.prodsuit.Repository.ServiceMediaRepository

class ServiceMediaViewModel: ViewModel()  {

    var serviceMediaData: MutableLiveData<ServiceMediaModel>? = null
    fun getserviceMedia(context: Context, ReqMode: String) : LiveData<ServiceMediaModel>? {
        serviceMediaData = ServiceMediaRepository.getServicesApiCall(context,ReqMode)
        return serviceMediaData
    }
}