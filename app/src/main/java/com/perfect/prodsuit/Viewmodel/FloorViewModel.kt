package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.FloorModel
import com.perfect.prodsuit.Repository.FloorRepository


class FloorViewModel: ViewModel() {

    var floorData: MutableLiveData<FloorModel>? = null

    fun getfloor_List(context: Context) : LiveData<FloorModel>? {
        floorData = FloorRepository.getServicesApiCall(context)
        return floorData
    }

}