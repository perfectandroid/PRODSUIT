package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.MaterialModeModel
import com.perfect.prodsuit.Repository.MaterialMoeRepository

class MaterialModeViewModel: ViewModel()  {

    var materialmodeData: MutableLiveData<MaterialModeModel>? = null

    fun getMaterialMode(context: Context) : LiveData<MaterialModeModel>? {
        materialmodeData = MaterialMoeRepository.getServicesApiCall(context)
        return materialmodeData
    }

}