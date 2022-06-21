package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ForgotMpinModel
import com.perfect.prodsuit.Repository.ForgotMpinActivityRepository

class ForgotMpinViewModel : ViewModel() {

    var forgotMpinLiveData: MutableLiveData<ForgotMpinModel>? = null

    fun forgotMpin(context: Context,mobileNumber : String) : LiveData<ForgotMpinModel>? {
        forgotMpinLiveData = ForgotMpinActivityRepository.getServicesApiCall(context,mobileNumber)
        return forgotMpinLiveData
    }
}