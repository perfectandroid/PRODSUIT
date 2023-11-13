package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.SiteCheckModel
import com.perfect.prodsuit.Repository.SiteCheckRepository

class SiteCheckViewModel : ViewModel()  {

    var siteCheckData: MutableLiveData<SiteCheckModel>? = null

    fun getSiteCheck(context: Context) : LiveData<SiteCheckModel>? {
        siteCheckData = SiteCheckRepository.getServicesApiCall(context)
        return siteCheckData
    }
}