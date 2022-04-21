package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.DistrictModel
import com.perfect.prodsuit.Repository.DistrictRepository

class DistrictViewModel  : ViewModel(){

    var districtLiveData: MutableLiveData<DistrictModel>? = null

    fun getDistrict(context: Context) : LiveData<DistrictModel>? {
        districtLiveData = DistrictRepository.getServicesApiCall(context)
        return districtLiveData
    }
}