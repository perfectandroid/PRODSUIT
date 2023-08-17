package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AuthApproveModel
import com.perfect.prodsuit.Repository.AuthApproveRepository


class AuthApproveViewModel : ViewModel() {

    var authApproveData: MutableLiveData<AuthApproveModel>? = null

    fun saveAuthApprove(context: Context, FK_AuthID : String) : LiveData<AuthApproveModel>? {
        authApproveData = AuthApproveRepository.getServicesApiCall(context,FK_AuthID)
        return authApproveData
    }
}