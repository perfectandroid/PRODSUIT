package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.EmployeeInventoryModel
import com.perfect.prodsuit.Repository.EmployeeInventoryRepository

class EmployeeInventoryViewModel : ViewModel() {

    var employeeInventoryData: MutableLiveData<EmployeeInventoryModel>? = null

    fun getEmployeeInventory(context: Context, Fk_Branch : String, Fk_Department : String) : LiveData<EmployeeInventoryModel>? {
        employeeInventoryData = EmployeeInventoryRepository.getServicesApiCall(context,Fk_Branch,Fk_Department)
        return employeeInventoryData
    }
}