package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.DepartmentInvetoryModel
import com.perfect.prodsuit.Repository.DepartmentInventoryRepository


class DepartmentInvertoryViewModel : ViewModel() {

    var departmentInvertoryData: MutableLiveData<DepartmentInvetoryModel>? = null

    fun getDepartmentInvetory(context: Context,FK_BranchTemp : String) : LiveData<DepartmentInvetoryModel>? {
        departmentInvertoryData = DepartmentInventoryRepository.getServicesApiCall(context,FK_BranchTemp)
        return departmentInvertoryData
    }
}