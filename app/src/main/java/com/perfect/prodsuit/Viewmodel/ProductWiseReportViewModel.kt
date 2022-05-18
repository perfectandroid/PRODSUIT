package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ProductWiseReportModel
import com.perfect.prodsuit.Repository.ProductWiseReportRepository

class ProductWiseReportViewModel   : ViewModel()  {
    var productWiseReportLiveData: MutableLiveData<ProductWiseReportModel>? = null
    fun getProductWiseReport(context: Context, strFromdate : String, strTodate : String, strDashboardTypeId : String) : LiveData<ProductWiseReportModel>? {
        productWiseReportLiveData = ProductWiseReportRepository.getServicesApiCall(context,strFromdate,strTodate,strDashboardTypeId)
        return productWiseReportLiveData
    }
}