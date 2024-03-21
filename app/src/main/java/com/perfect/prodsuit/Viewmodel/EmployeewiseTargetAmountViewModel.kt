package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.EmployeewisetargetAmountModel
import com.perfect.prodsuit.Repository.EmployeeWiseTargetAmountRepository

class EmployeewiseTargetAmountViewModel : ViewModel() {

    var empwiseTargetAmounttData: MutableLiveData<EmployeewisetargetAmountModel>? = null
    fun getEmployeewisetargetAmountChart(context: Context) : MutableLiveData<EmployeewisetargetAmountModel>? {
        empwiseTargetAmounttData = EmployeeWiseTargetAmountRepository.getServicesApiCall(context)
        return empwiseTargetAmounttData
    }
}