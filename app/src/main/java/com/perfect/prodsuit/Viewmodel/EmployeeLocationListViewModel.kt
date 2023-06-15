package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.EmployeeLocationListModel
import com.perfect.prodsuit.Repository.EmployeeLocationListRepository

class EmployeeLocationListViewModel : ViewModel()  {
    var employeeLocationData: MutableLiveData<EmployeeLocationListModel>? = null

    fun getEmployeeLocationList(context: Context,strDate : String,ID_Department : String,ID_Designation : String,ID_Employee : String) : LiveData<EmployeeLocationListModel>? {
        employeeLocationData = EmployeeLocationListRepository.getServicesApiCall(context,strDate,ID_Department,ID_Designation,ID_Employee)
        return employeeLocationData
    }
}