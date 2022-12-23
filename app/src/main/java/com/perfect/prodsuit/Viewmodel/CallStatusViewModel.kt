package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CallStatusModel
import com.perfect.prodsuit.Repository.CallStatusRepository

class CallStatusViewModel  : ViewModel() {

    var callStatusData: MutableLiveData<CallStatusModel>? = null

    fun getCallStatus(context: Context) : MutableLiveData<CallStatusModel>? {
        callStatusData = CallStatusRepository.getServicesApiCall(context)
        return callStatusData
    }
}