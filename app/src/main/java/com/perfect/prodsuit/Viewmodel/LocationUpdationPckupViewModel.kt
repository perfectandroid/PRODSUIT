//package com.perfect.prodsuit.Viewmodel
//
//import android.content.Context
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.perfect.prodsuit.Model.LocationUpdationPickupModel
//import com.perfect.prodsuit.Repository.LocationUpdationPickupRespository
//
//class LocationUpdationPckupViewModel : ViewModel(){
//
//    var LocationUpdationData: MutableLiveData<LocationUpdationPickupModel>? = null
//
//    fun getLocationUpdation(context: Context) : MutableLiveData<LocationUpdationPickupModel>? {
//        LocationUpdationData = LocationUpdationPickupRespository.getServicesApiCall(context)
//        return LocationUpdationData
//    }
//}