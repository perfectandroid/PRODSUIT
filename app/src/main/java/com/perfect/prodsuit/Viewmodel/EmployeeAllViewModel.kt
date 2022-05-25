package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.EmployeeAllModel
import com.perfect.prodsuit.Repository.EmployeeAllRepository

class EmployeeAllViewModel: ViewModel()  {

    var employeeAllData: MutableLiveData<EmployeeAllModel>? = null
    fun getEmployeeAll(context: Context) : LiveData<EmployeeAllModel>? {
        employeeAllData = EmployeeAllRepository.getServicesApiCall(context)
        return employeeAllData
    }
}