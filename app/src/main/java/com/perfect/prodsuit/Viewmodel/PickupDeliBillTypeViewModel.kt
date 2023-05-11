package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.PickupDeliBillTypeModel
import com.perfect.prodsuit.Repository.PickupDeliBillTypeRepository

class PickupDeliBillTypeViewModel  : ViewModel() {

    var PickupDeliBillTypeData: MutableLiveData<PickupDeliBillTypeModel>? = null

    fun getPickupDeliBillType(context: Context) : LiveData<PickupDeliBillTypeModel>? {
        PickupDeliBillTypeData = PickupDeliBillTypeRepository.getServicesApiCall(context)
        return PickupDeliBillTypeData
    }
}