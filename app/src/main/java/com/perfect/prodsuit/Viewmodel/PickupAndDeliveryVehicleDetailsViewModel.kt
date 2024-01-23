package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.PickupAndDeliveryVehicleDetailsModel
import com.perfect.prodsuit.Repository.PickupAndDeliveryVehicleDetailRepository

class PickupAndDeliveryVehicleDetailsViewModel  : ViewModel() {

    var pickupAndDeliveryVehicleDetailsData: MutableLiveData<PickupAndDeliveryVehicleDetailsModel>? = null

    fun getPickupAndDeliveryVehicleDetailsData(context: Context,TransDate : String,DashMode : String,DashType : String) : LiveData<PickupAndDeliveryVehicleDetailsModel>? {
        pickupAndDeliveryVehicleDetailsData = PickupAndDeliveryVehicleDetailRepository.getServicesApiCall(context,TransDate,DashMode,DashType)
        return pickupAndDeliveryVehicleDetailsData
    }
}

