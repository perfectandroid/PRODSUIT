package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ProjectLeadNoModel
import com.perfect.prodsuit.Repository.ProjectLeadNoRepository

class ProjectLeadNoViewModel : ViewModel() {

    var projectLeadNoData: MutableLiveData<ProjectLeadNoModel>? = null

    fun getProjectLeadNo(context: Context) : LiveData<ProjectLeadNoModel>? {
        projectLeadNoData = ProjectLeadNoRepository.getServicesApiCall(context)
        return projectLeadNoData
    }
}