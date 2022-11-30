package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.OTPModel
import com.perfect.prodsuit.Repository.OTPActivityRepository
import com.perfect.prodsuit.View.Activity.OTPActivity

class OTPActivityViewModel : ViewModel() {

    var otpLiveData: MutableLiveData<OTPModel>? = null

    fun getOTP(context: Context,strMOTP :  String) : LiveData<OTPModel>? {
        otpLiveData = OTPActivityRepository.getServicesApiCall(context, strMOTP)
        return otpLiveData
    }

}