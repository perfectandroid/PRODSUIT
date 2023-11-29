package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ProjectDelayedModel

import com.perfect.prodsuit.Repository.ProjectBillStatusRepository
import com.perfect.prodsuit.Repository.ProjectDelayedStatusRepository


class ProjectDelayedViewModel : ViewModel() {

    var projectDealyedLiveData: MutableLiveData<ProjectDelayedModel>? = null

    fun getProjectDealyedStatus(context: Context) : LiveData<ProjectDelayedModel>? {
        projectDealyedLiveData = ProjectDelayedStatusRepository.getServicesApiCall(context)
        return projectDealyedLiveData
    }
}