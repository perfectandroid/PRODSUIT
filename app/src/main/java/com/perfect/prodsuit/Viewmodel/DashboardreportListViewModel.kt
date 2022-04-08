package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.BannerModel
import com.perfect.prodsuit.Model.DashboardReportModel
import com.perfect.prodsuit.Repository.BannersRepository
import com.perfect.prodsuit.Repository.DashboardReportRepository

class DashboardreportListViewModel : ViewModel() {

    var dashboardreportlistLiveData: MutableLiveData<DashboardReportModel>? = null

    fun getDashboardreportlist(context: Context) : LiveData<DashboardReportModel>? {
        dashboardreportlistLiveData = DashboardReportRepository.getServicesApiCall(context)
        return dashboardreportlistLiveData
    }

}