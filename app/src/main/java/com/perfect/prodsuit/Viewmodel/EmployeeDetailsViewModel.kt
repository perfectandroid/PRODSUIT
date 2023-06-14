package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.EmployeeDetailsModel
import com.perfect.prodsuit.Repository.EmployeeDetailsRepository


class EmployeeDetailsViewModel: ViewModel()  {
    var employeeData: MutableLiveData<EmployeeDetailsModel>? = null
    fun getEmployee(context: Context, ID_Department: String,ID_Designation : String) : LiveData<EmployeeDetailsModel>? {
        employeeData = EmployeeDetailsRepository.getServicesApiCall(context,ID_Department,ID_Designation)
        return employeeData
    }

}