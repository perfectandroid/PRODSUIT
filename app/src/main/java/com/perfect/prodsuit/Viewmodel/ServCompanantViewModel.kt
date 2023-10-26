package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServCompanantModel
import com.perfect.prodsuit.Repository.ServCompanantRepository

class ServCompanantViewModel : ViewModel() {

    var servCompanantLiveData: MutableLiveData<ServCompanantModel>? = null

    fun getServCompanant(context: Context,TransMode : String,ReqMode : String) : LiveData<ServCompanantModel>? {
        servCompanantLiveData = ServCompanantRepository.getServicesApiCall(context,TransMode,ReqMode)
        return servCompanantLiveData
    }
}