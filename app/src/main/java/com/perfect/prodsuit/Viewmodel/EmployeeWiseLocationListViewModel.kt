package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.EmployeeWiseLocationListModel
import com.perfect.prodsuit.Repository.EmployeeWiseLocationListRepository

class EmployeeWiseLocationListViewModel: ViewModel()  {
    var employeeWiseLocationListData: MutableLiveData<EmployeeWiseLocationListModel>? = null
    fun getEmployeeWiseLocationList(context: Context, FK_Employee: String, strDate : String) : LiveData<EmployeeWiseLocationListModel>? {
        employeeWiseLocationListData = EmployeeWiseLocationListRepository.getServicesApiCall(context,FK_Employee,strDate)
        return employeeWiseLocationListData
    }
}