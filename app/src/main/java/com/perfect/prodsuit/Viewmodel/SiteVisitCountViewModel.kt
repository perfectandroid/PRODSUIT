package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.SiteVisitCountModel
import com.perfect.prodsuit.Repository.SiteVisitCountRepository

class SiteVisitCountViewModel: ViewModel() {

    var siteVisitCountData: MutableLiveData<SiteVisitCountModel>? = null

    fun getSiteVisitCount(context: Context) : LiveData<SiteVisitCountModel>? {
        siteVisitCountData = SiteVisitCountRepository.getServicesApiCall(context)
        return siteVisitCountData
    }
}