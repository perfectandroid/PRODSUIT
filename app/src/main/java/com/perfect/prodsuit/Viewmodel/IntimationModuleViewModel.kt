package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.IntimationModuleModel
import com.perfect.prodsuit.Repository.IntimationModuleRepository

class IntimationModuleViewModel : ViewModel() {

    var moduleData: MutableLiveData<IntimationModuleModel>? = null

    fun getModule(context: Context,SubMode : String) : LiveData<IntimationModuleModel>? {
        moduleData = IntimationModuleRepository.getServicesApiCall(context,SubMode)
        return moduleData
    }
}