package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.EmployeeModel
import com.perfect.prodsuit.Repository.EmployeeRepository

class EmployeeViewModel: ViewModel()  {

    var employeeData: MutableLiveData<EmployeeModel>? = null
    fun getEmployee(context: Context) : LiveData<EmployeeModel>? {
        employeeData = EmployeeRepository.getServicesApiCall(context)
        return employeeData
    }

}