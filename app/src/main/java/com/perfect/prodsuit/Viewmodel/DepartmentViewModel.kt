package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.DepartmentModel
import com.perfect.prodsuit.Reprository.DepartmentRepository


class DepartmentViewModel: ViewModel()  {

    var departmenthData: MutableLiveData<DepartmentModel>? = null

    fun getDepartment(context: Context) : LiveData<DepartmentModel>? {
        departmenthData = DepartmentRepository.getServicesApiCall(context)
        return departmenthData
    }
}