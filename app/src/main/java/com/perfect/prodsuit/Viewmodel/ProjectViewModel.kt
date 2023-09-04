package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ProjectModel
import com.perfect.prodsuit.Repository.ProjectRepository

class ProjectViewModel : ViewModel()  {

    var projectData: MutableLiveData<ProjectModel>? = null

    fun getprojectV_Model(context: Context) : LiveData<ProjectModel>? {
        projectData = ProjectRepository.getServicesApiCall(context)
        return projectData
    }

}