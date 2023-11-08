package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.MetarialUsageStageModel
import com.perfect.prodsuit.Repository.MaterialUsagestageRepository

class MaterialUsageStageViewModel : ViewModel()  {

    var materialusageStagetData: MutableLiveData<MetarialUsageStageModel>? = null

    fun getMaterialUsageStageModel(context: Context,ID_Project : String,ReqMode : String) : LiveData<MetarialUsageStageModel>? {
        materialusageStagetData = MaterialUsagestageRepository.getServicesApiCall(context,ID_Project,ReqMode)
        return materialusageStagetData
    }

}