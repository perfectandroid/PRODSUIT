package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ProjectPayInfoModel
import com.perfect.prodsuit.Repository.ProjectPayInfoRepository

class ProjectPayInfoViewModel : ViewModel() {

    var projectPayInfoData: MutableLiveData<ProjectPayInfoModel>? = null

    fun getProjectPayInfo(context: Context, ID_Project : String, ID_Stage : String, ID_PettyCashier : String, ID_Employee : String) : LiveData<ProjectPayInfoModel>? {
        projectPayInfoData = ProjectPayInfoRepository.getServicesApiCall(context,ID_Project,ID_Stage,ID_PettyCashier,ID_Employee)
        return projectPayInfoData
    }
}