package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.MaterialUsageTeamModel
import com.perfect.prodsuit.Repository.MaterialUsageTeamRepository

class MaterialUsageTeamViewModel : ViewModel()  {

    var materialusageTeamtData: MutableLiveData<MaterialUsageTeamModel>? = null

    fun getMaterialUsageTeamModel(context: Context,ID_Project : String,ID_Stage : String) : LiveData<MaterialUsageTeamModel>? {
        materialusageTeamtData = MaterialUsageTeamRepository.getServicesApiCall(context,ID_Project,ID_Stage)
        return materialusageTeamtData
    }

}