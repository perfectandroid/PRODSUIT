package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LoginModel
import com.perfect.prodsuit.Reprository.LoginActivityRepository

class LoginActivityViewModel : ViewModel() {

    var loginLiveData: MutableLiveData<LoginModel>? = null

    fun getUser(context: Context) : LiveData<LoginModel>? {
        loginLiveData = LoginActivityRepository.getServicesApiCall(context)
        return loginLiveData
    }

}