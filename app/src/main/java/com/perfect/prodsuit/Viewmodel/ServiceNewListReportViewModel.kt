package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceNewListReportModel
import com.perfect.prodsuit.Repository.ServiceNewListReportRepository

class ServiceNewListReportViewModel  : ViewModel() {

    var serviceNewListLiveData: MutableLiveData<ServiceNewListReportModel>? = null

    fun getserviceNewList(context: Context,ReportMode: String, ID_Branch: String, ID_Employee: String, strFromdate: String, strTodate: String, ID_Product: String?,
                          ID_CompService: String, ID_ComplaintList: String) : LiveData<ServiceNewListReportModel>? {
        serviceNewListLiveData = ServiceNewListReportRepository.getserviceNewList(context,ReportMode, ID_Branch, ID_Employee, strFromdate, strTodate, ID_Product, ID_CompService, ID_ComplaintList)
        return serviceNewListLiveData
    }
}