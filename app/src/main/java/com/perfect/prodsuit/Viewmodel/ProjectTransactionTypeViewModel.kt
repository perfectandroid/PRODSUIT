package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ProjectTransactionTypeModel
import com.perfect.prodsuit.Repository.ProjectTransactionTypeRepository

class ProjectTransactionTypeViewModel : ViewModel() {

    var projectTransactionTypeData: MutableLiveData<ProjectTransactionTypeModel>? = null
    fun getProjectTransactionType(context: Context) : LiveData<ProjectTransactionTypeModel>? {
        projectTransactionTypeData = ProjectTransactionTypeRepository.getServicesApiCall(context)
        return projectTransactionTypeData
    }
}