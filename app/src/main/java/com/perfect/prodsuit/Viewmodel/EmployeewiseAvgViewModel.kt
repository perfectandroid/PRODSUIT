package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.EmpAvgConvModel
import com.perfect.prodsuit.Repository.EmpAvgConvRepository


class EmployeewiseAvgViewModel : ViewModel() {

    var empAvgConvData: MutableLiveData<EmpAvgConvModel>? = null

    fun getEmpAvgConversion(context: Context) : LiveData<EmpAvgConvModel>? {
        empAvgConvData = EmpAvgConvRepository.getServicesApiCall(context)
        return empAvgConvData
    }
}