package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.PincodeSearchModel
import com.perfect.prodsuit.Repository.PincodeSearchRepository

class PinCodeSearchViewModel : ViewModel() {

    var pincodeLiveData: MutableLiveData<PincodeSearchModel>? = null

    fun getPincode(context: Context) : LiveData<PincodeSearchModel>? {
        pincodeLiveData = PincodeSearchRepository.getServicesApiCall(context)
        return pincodeLiveData
    }
}