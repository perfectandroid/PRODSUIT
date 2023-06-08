package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.EmployeeLocationUpdateModel
import com.perfect.prodsuit.Repository.EmployeeLocationUpdateRepository

class EmployeeLocationUpdateViewModel : ViewModel(){
    var employeeLocationUpdateData: MutableLiveData<EmployeeLocationUpdateModel>? = null

    fun UpdateEmployeeLocation(context: Context, LocLatitude : String, LocLongitude : String,LocationAddress  :String, ChargePercentage : String) : LiveData<EmployeeLocationUpdateModel>? {
        employeeLocationUpdateData = EmployeeLocationUpdateRepository.getServicesApiCall(context,LocLatitude,LocLongitude,LocationAddress,ChargePercentage)
        return employeeLocationUpdateData
    }
}
