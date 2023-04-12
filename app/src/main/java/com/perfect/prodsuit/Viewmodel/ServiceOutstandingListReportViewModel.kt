package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.perfect.prodsuit.Model.ServiceOutstandingListReportModel
import com.perfect.prodsuit.Repository.ServiceOutstandingListReportRepository

class ServiceOutstandingListReportViewModel  : ViewModel() {

    var serviceOutstandingListLiveData: MutableLiveData<ServiceOutstandingListReportModel>? = null

    fun getserviceOutstandingList(context: Context, ReportMode: String, ID_Branch: String, ID_Employee: String, strFromdate: String, strTodate: String, ID_Product: String?,
                          ID_CompService: String, ID_ComplaintList: String) : LiveData<ServiceOutstandingListReportModel>? {
        serviceOutstandingListLiveData = ServiceOutstandingListReportRepository.getserviceOutstandingList(context,ReportMode, ID_Branch, ID_Employee, strFromdate, strTodate, ID_Product, ID_CompService, ID_ComplaintList)
        return serviceOutstandingListLiveData
    }
}