package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.BannerModel
import com.perfect.prodsuit.Model.ReportvieModel
import com.perfect.prodsuit.Repository.BannersRepository
import com.perfect.prodsuit.Repository.ReportViewRepository

class ReportviewViewModel : ViewModel() {

    var reportviewLiveData: MutableLiveData<ReportvieModel>? = null

    fun getReportview(context: Context) : LiveData<ReportvieModel>? {
        reportviewLiveData = ReportViewRepository.getServicesApiCall(context)
        return reportviewLiveData
    }

}