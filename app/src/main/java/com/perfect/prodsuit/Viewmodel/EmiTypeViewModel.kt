package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.EmiTypeModel
import com.perfect.prodsuit.Repository.EmiTypeRepository

class EmiTypeViewModel : ViewModel()  {

    var emiTypeData: MutableLiveData<EmiTypeModel>? = null
    fun getEmiType(context: Context) : LiveData<EmiTypeModel>? {
        emiTypeData = EmiTypeRepository.getServicesApiCall(context)
        return emiTypeData
    }
}