package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.StockRequestModel
import com.perfect.prodsuit.Repository.StockRequestRepository

class StockRequestViewModel  : ViewModel() {

    var stockRequestData: MutableLiveData<StockRequestModel>? = null

    fun getStockRequest(context: Context,FK_BranchFrom : String,FK_DepartmentFrom: String,FK_EmployeeFrom: String,FK_BranchTo: String,FK_DepartmentTo: String,FK_EmployeeTo: String) : LiveData<StockRequestModel>? {
        stockRequestData = StockRequestRepository.getServicesApiCall(context,FK_BranchFrom,FK_DepartmentFrom,FK_EmployeeFrom,FK_BranchTo,FK_DepartmentTo,FK_EmployeeTo)
        return stockRequestData
    }
}