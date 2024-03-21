package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.EmployeewiseModel
import com.perfect.prodsuit.Repository.EmployeeWiseRepository

class EmployeewiseViewModel : ViewModel() {

    var actionListTicketReportData: MutableLiveData<EmployeewiseModel>? = null
    fun getEmployeewiseChart(context: Context) : MutableLiveData<EmployeewiseModel>? {
        actionListTicketReportData = EmployeeWiseRepository.getServicesApiCall(context)
        return actionListTicketReportData
    }
}