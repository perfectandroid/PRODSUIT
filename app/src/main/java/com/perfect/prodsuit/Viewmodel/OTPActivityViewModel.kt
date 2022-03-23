package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.OTPModel
import com.perfect.prodsuit.Reprository.OTPActivityRepository

class OTPActivityViewModel : ViewModel() {

    var otpLiveData: MutableLiveData<OTPModel>? = null

    fun getOTP(context: Context) : LiveData<OTPModel>? {
        otpLiveData = OTPActivityRepository.getServicesApiCall(context)
        return otpLiveData
    }

}