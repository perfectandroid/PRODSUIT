package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.PickupAndDeliveryOrderTrackingTileModel
import com.perfect.prodsuit.Repository.PickupAndDeliveryOrderTrackingTileRepository

class PickupAndDeliveryOrderTrackingTileViewModel  : ViewModel() {

    var pickupAndDeliveryOrderTrackingTileData: MutableLiveData<PickupAndDeliveryOrderTrackingTileModel>? = null

    fun getPickupAndDeliveryOrderTrackingTileData(context: Context, TransDate : String, DashMode : String, DashType : String) : LiveData<PickupAndDeliveryOrderTrackingTileModel>? {
        pickupAndDeliveryOrderTrackingTileData = PickupAndDeliveryOrderTrackingTileRepository.getServicesApiCall(context,TransDate,DashMode,DashType)
        return pickupAndDeliveryOrderTrackingTileData
    }
}