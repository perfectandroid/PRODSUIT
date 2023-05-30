package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CustomerSearchModel
import com.perfect.prodsuit.Model.LocationUpdateModel
import com.perfect.prodsuit.Repository.CustomerSearchRepository
import com.perfect.prodsuit.Repository.LocationUpdateRepository

class LocationUpdateViewModel  : ViewModel(){

    var locationUpdateLiveData: MutableLiveData<LocationUpdateModel>? = null

    fun getLocationUpdate(context: Context,TransMode  : String,ID_ImageLocation : String, FK_Master : String,updateLatitude : String,updateLongitude : String,updateAddress : String) : LiveData<LocationUpdateModel>? {
        locationUpdateLiveData = LocationUpdateRepository.getServicesApiCall(context,TransMode,ID_ImageLocation,FK_Master,updateLongitude,updateLatitude,updateAddress)
        return locationUpdateLiveData
    }
}