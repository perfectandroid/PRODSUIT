package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.UnitModel
import com.perfect.prodsuit.Repository.UnitRepository
import com.perfect.prodsuit.Repository.WorkTypeRepository

class UnitViewModel : ViewModel()  {

    var uniteData: MutableLiveData<UnitModel>? = null

    fun getUnit(context: Context,ReqMode : String) : LiveData<UnitModel>? {
        uniteData = UnitRepository.getServicesApiCall(context,ReqMode)
        return uniteData
    }

}