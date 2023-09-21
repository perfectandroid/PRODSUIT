package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.DashboardCommonModel
import com.perfect.prodsuit.Repository.DashboardCommonRepository

class DashboardCommonViewModel  : ViewModel()  {

    var dashboardCommonData: MutableLiveData<DashboardCommonModel>? = null

    fun  getDashboardCommon(context: Context, strCustomer : String) : MutableLiveData<DashboardCommonModel>? {
        dashboardCommonData = DashboardCommonRepository.getServicesApiCall(context,strCustomer)
        return dashboardCommonData
    }
}