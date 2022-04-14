package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.SaveSiteVisitModel
import com.perfect.prodsuit.Repository.SaveSiteVisitRepository

class SaveSiteVisitViewModel : ViewModel() {

    var saveSiteVisitData: MutableLiveData<SaveSiteVisitModel>? = null
    fun saveSiteVisit(context: Context) : LiveData<SaveSiteVisitModel>? {
        saveSiteVisitData = SaveSiteVisitRepository.getServicesApiCall(context)
        return saveSiteVisitData
    }
}