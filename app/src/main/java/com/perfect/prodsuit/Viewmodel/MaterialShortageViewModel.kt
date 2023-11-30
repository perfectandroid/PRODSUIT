package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.MaterialShortageModel
import com.perfect.prodsuit.Repository.MaterialShortageRespository

class MaterialShortageViewModel : ViewModel()  {

    var MaterialShortageData: MutableLiveData<MaterialShortageModel>? = null

    fun getMaterialShortage(context: Context,TransDate: String) : LiveData<MaterialShortageModel>? {
        MaterialShortageData = MaterialShortageRespository.getServicesApiCall(context,TransDate)
        return MaterialShortageData
    }

}