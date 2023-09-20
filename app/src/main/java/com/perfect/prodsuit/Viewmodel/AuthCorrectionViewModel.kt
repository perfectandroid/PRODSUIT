package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AuthCorrectionModel
import com.perfect.prodsuit.Repository.AuthCorrectionRepository

class AuthCorrectionViewModel : ViewModel() {

    var authCorrectionData: MutableLiveData<AuthCorrectionModel>? = null

    fun getAuthCorrection(context: Context, AuthID : String, ID_Reason : String, strReason : String) : LiveData<AuthCorrectionModel>? {
        authCorrectionData = AuthCorrectionRepository.getServicesApiCall(context,AuthID,ID_Reason,strReason)
        return authCorrectionData
    }
}