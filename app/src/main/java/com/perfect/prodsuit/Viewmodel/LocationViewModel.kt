package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.BannerModel
import com.perfect.prodsuit.Model.LocationModel
import com.perfect.prodsuit.Repository.BannersRepository
import com.perfect.prodsuit.Repository.LocationRepository

class LocationViewModel : ViewModel() {

    var locationLiveData: MutableLiveData<LocationModel>? = null

    fun getLocation(context: Context) : LiveData<LocationModel>? {
        locationLiveData = LocationRepository.getServicesApiCall(context)
        return locationLiveData
    }

}