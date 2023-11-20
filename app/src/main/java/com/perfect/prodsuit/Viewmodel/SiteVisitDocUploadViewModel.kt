package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.SiteVisitDocUploadModel
import com.perfect.prodsuit.Repository.SiteVisitDocUploadRepository

class SiteVisitDocUploadViewModel : ViewModel()  {

    var siteVisitDocUploadData: MutableLiveData<SiteVisitDocUploadModel>? = null

    fun getSiteVisitDocUpload(context: Context, TransMode : String, FK_SiteVisit : String, ProjImageName : String,
                                     ProjImageType : String, ProjImageDescription : String, ProjImage : String) : LiveData<SiteVisitDocUploadModel>? {
        siteVisitDocUploadData = SiteVisitDocUploadRepository.getServicesApiCall(context,TransMode,FK_SiteVisit,ProjImageName,ProjImageType,
            ProjImageDescription,ProjImage)
        return siteVisitDocUploadData
    }
}