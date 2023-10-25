package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AddServiceModel
import com.perfect.prodsuit.Repository.AddServiceRepository

class AddServiceViewModel  : ViewModel() {

    var addServiceLiveData: MutableLiveData<AddServiceModel>? = null

    fun getAddService(context: Context) : LiveData<AddServiceModel>? {
        addServiceLiveData = AddServiceRepository.getServicesApiCall(context)
        return addServiceLiveData
    }
}