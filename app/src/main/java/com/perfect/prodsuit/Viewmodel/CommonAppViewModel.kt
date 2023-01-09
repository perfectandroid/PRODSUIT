package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CommonAppModel
import com.perfect.prodsuit.Repository.CommonAppRepository

class CommonAppViewModel : ViewModel()  {

    var commonAppLiveData: MutableLiveData<CommonAppModel>? = null

    fun getCommonApp(context: Context) : LiveData<CommonAppModel>? {
        commonAppLiveData = CommonAppRepository.getServicesApiCall(context)
        return commonAppLiveData
    }
}