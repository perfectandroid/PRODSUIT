package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AreaModel
import com.perfect.prodsuit.Repository.AreaRepository


class AreaViewModel : ViewModel() {

    var areaLiveData: MutableLiveData<AreaModel>? = null

    fun getArea(context: Context, FK_District : String) : LiveData<AreaModel>? {
        areaLiveData = AreaRepository.getServicesApiCall(context,FK_District)
        return areaLiveData
    }
}