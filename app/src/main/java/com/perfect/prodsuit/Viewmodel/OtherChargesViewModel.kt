package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.OtherchargeModel
import com.perfect.prodsuit.Repository.OtherchargeRepository

class OtherChargesViewModel : ViewModel() {

    var otherchargeData: MutableLiveData<OtherchargeModel>? = null

    fun getOthercharge(context: Context) : LiveData<OtherchargeModel>? {
        otherchargeData = OtherchargeRepository.getServicesApiCall(context)
        return otherchargeData
    }
}