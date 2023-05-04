package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.PickupDeliStandByProductModel
import com.perfect.prodsuit.Repository.PickupDeliStandByProductRespository

class PickupDeliStandByProductViewModel : ViewModel(){

    var PickupDeliStandByProductData: MutableLiveData<PickupDeliStandByProductModel>? = null

    fun getPickupDeliStandByProduct(context: Context) : LiveData<PickupDeliStandByProductModel>? {
        PickupDeliStandByProductData = PickupDeliStandByProductRespository.getServicesApiCall(context)
        return PickupDeliStandByProductData
    }
}