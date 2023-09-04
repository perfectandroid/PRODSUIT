package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.MaterialUsageProjectModel
import com.perfect.prodsuit.Repository.MaterialUsageProjectRepository

class MaterialUsageProjectViewModel: ViewModel()  {

    var materialusageProjectData: MutableLiveData<MaterialUsageProjectModel>? = null

    fun getMaterialUsageProjectModel(context: Context) : LiveData<MaterialUsageProjectModel>? {
        materialusageProjectData = MaterialUsageProjectRepository.getServicesApiCall(context)
        return materialusageProjectData
    }

}
