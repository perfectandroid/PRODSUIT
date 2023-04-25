package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.GetGenralSettingsModel
import com.perfect.prodsuit.Repository.GetGenralSettingsRepository

class GetGenralSettingsViewModel : ViewModel()  {
    var getGenralSettingsData: MutableLiveData<GetGenralSettingsModel>? = null

    fun getRequestLicence(context: Context,nameKey : String) : MutableLiveData<GetGenralSettingsModel>? {
        getGenralSettingsData = GetGenralSettingsRepository.getServicesApiCall(context,nameKey)
        return getGenralSettingsData
    }
}