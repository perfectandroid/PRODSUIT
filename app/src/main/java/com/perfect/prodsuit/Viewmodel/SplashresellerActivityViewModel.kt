package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ResellerModel
import com.perfect.prodsuit.Repository.SplashresellerActivityRepository

class SplashresellerActivityViewModel : ViewModel() {

    var resellerLiveData: MutableLiveData<ResellerModel>? = null

    fun getReseller(context: Context) : LiveData<ResellerModel>? {
        resellerLiveData = SplashresellerActivityRepository.getServicesApiCall(context)
        return resellerLiveData
    }

}