package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ActionListTicketReportModel
import com.perfect.prodsuit.Model.DashboardModuleModel
import com.perfect.prodsuit.Repository.ActionListTicketReportRepository
import com.perfect.prodsuit.Repository.DashboardModuleRepository

class DashboardModuleViewModel : ViewModel() {

    var dashboardModuleData: MutableLiveData<DashboardModuleModel>? = null
    fun getDashboardModuleListReport(context: Context) : MutableLiveData<DashboardModuleModel>? {
        dashboardModuleData = DashboardModuleRepository.getServicesApiCall(context)
        return dashboardModuleData
    }
}