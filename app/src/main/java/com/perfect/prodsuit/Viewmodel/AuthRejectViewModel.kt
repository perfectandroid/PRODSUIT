package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AuthRejectModel
import com.perfect.prodsuit.Repository.AuthRejectRepository

class AuthRejectViewModel : ViewModel() {

    var authRejectData: MutableLiveData<AuthRejectModel>? = null

    fun saveAuthReject(context: Context, FK_AuthID : String, ID_Reason : String, strReason : String) : LiveData<AuthRejectModel>? {
        authRejectData = AuthRejectRepository.getServicesApiCall(context,FK_AuthID,ID_Reason,strReason)
        return authRejectData
    }
}