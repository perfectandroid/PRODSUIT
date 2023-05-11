package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.EMIAccountDetailsModel
import com.perfect.prodsuit.Repository.EmiAccountDetailsRepository

class EMIAccountDetailsViewModel : ViewModel() {
    var emiAccountDetailstData: MutableLiveData<EMIAccountDetailsModel>? = null
    fun getEmiAccountDetailst(context: Context, ID_CustomerWiseEMI: String,strCurrentDate : String) : MutableLiveData<EMIAccountDetailsModel>? {
        emiAccountDetailstData = EmiAccountDetailsRepository.getServicesApiCall(context,ID_CustomerWiseEMI,strCurrentDate)
        return emiAccountDetailstData
    }
}