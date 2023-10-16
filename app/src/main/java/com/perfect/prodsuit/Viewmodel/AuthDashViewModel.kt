package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AuthDashModel
import com.perfect.prodsuit.Repository.AuthDashRepository

class AuthDashViewModel  : ViewModel() {

    var authDashData: MutableLiveData<AuthDashModel>? = null
    fun getAuthdashboard(context: Context) : LiveData<AuthDashModel>? {
        authDashData = AuthDashRepository.getServicesApiCall(context)
        return authDashData
    }
}