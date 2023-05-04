package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.PickupDeliveryProductModel
import com.perfect.prodsuit.Repository.PickupDeliveryProductRepository

class PickupDeliveryProductViewModel : ViewModel(){

    var PickupDeliveryProductData: MutableLiveData<PickupDeliveryProductModel>? = null

    fun getPickupDeliveryProduct(context: Context) : LiveData<PickupDeliveryProductModel>? {
        PickupDeliveryProductData = PickupDeliveryProductRepository.getServicesApiCall(context)
        return PickupDeliveryProductData
    }
}