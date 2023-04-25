package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.EmployeeListModel
import com.perfect.prodsuit.Repository.EmployeeListRepository

class EmployeeeListViewModel : ViewModel() {

    var employeelistdata: MutableLiveData<EmployeeListModel>? = null

    fun getEmployeeList(context: Context, ID_Employee: String) : LiveData<EmployeeListModel>? {
        employeelistdata = EmployeeListRepository.getServicesApiCall(context,ID_Employee)
        return employeelistdata
    }

}