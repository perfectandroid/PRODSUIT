package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.MaterialUsageTeamModel
import com.perfect.prodsuit.Repository.MaterialUsageTeamRepository

class MaterialUsageTeamViewModel : ViewModel()  {

    var materialusageTeamtData: MutableLiveData<MaterialUsageTeamModel>? = null

    fun getMaterialUsageTeamModel(context: Context) : LiveData<MaterialUsageTeamModel>? {
        materialusageTeamtData = MaterialUsageTeamRepository.getServicesApiCall(context)
        return materialusageTeamtData
    }

}