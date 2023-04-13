package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceListReportModel
import com.perfect.prodsuit.Repository.ServiceListReportRepository

class ServiceListReportViewModel : ViewModel() {

    var serviceListLiveData: MutableLiveData<ServiceListReportModel>? = null

    fun getserviceList(context: Context, ReportMode: String, ID_Branch: String, ID_Employee: String, strFromdate: String, strTodate: String, ID_Product: String?,
                       ID_CompService: String, ID_ComplaintList: String) : LiveData<ServiceListReportModel>? {
        serviceListLiveData = ServiceListReportRepository.getserviceList(context,ReportMode, ID_Branch, ID_Employee, strFromdate, strTodate, ID_Product, ID_CompService, ID_ComplaintList)
        return serviceListLiveData
    }
}