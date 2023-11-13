package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ProjectFollowupSaveModel
import com.perfect.prodsuit.Repository.ProjectFollowupSaveRepository

class ProjectFollowupSaveViewModel : ViewModel()  {

    var projectFollowupSaveData: MutableLiveData<ProjectFollowupSaveModel>? = null

    fun saveProjectFollowup(context: Context,UserAction : String,ID_Project : String,ID_Stage : String,strFollowupdate : String,ID_CurrentStatus : String,
                            strStatudate : String,strReason : String,strRemarks : String) : LiveData<ProjectFollowupSaveModel>? {
        projectFollowupSaveData = ProjectFollowupSaveRepository.getServicesApiCall(context,UserAction,ID_Project ,ID_Stage ,strFollowupdate,ID_CurrentStatus,
            strStatudate ,strReason ,strRemarks)
        return projectFollowupSaveData
    }
}