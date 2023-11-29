package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ProjectBillStatusModel
import com.perfect.prodsuit.Repository.ProjectBillStatusRepository


class ProjectBillStatusViewModel : ViewModel() {

    var projectBillLiveData: MutableLiveData<ProjectBillStatusModel>? = null

    fun getProjectBillStatus(context: Context) : LiveData<ProjectBillStatusModel>? {
        projectBillLiveData = ProjectBillStatusRepository.getServicesApiCall(context)
        return projectBillLiveData
    }
}