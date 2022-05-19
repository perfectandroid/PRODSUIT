package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ReportNameModel
import com.perfect.prodsuit.Repository.ReportNameRepository

class ReportNameViewModel : ViewModel() {

    var reportNameData: MutableLiveData<ReportNameModel>? = null

    fun getReportName(context: Context) : LiveData<ReportNameModel>? {
        reportNameData = ReportNameRepository.getServicesApiCall(context)
        return reportNameData
    }
}