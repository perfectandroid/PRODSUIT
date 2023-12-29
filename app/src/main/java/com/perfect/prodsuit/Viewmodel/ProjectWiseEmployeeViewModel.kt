package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ProjectWiseEmployeeModel
import com.perfect.prodsuit.Repository.ProjectWiseEmployeeRepository

class ProjectWiseEmployeeViewModel : ViewModel()  {

    var projectWiseEmployee: MutableLiveData<ProjectWiseEmployeeModel>? = null
    fun getProjectWiseEmployee(context: Context, ID_Project: String, ID_Stage: String, Criteria: String, ReqMode: String) : LiveData<ProjectWiseEmployeeModel>? {
        projectWiseEmployee = ProjectWiseEmployeeRepository.getServicesApiCall(context,ID_Project,ID_Stage,Criteria,ReqMode)
        return projectWiseEmployee
    }
}