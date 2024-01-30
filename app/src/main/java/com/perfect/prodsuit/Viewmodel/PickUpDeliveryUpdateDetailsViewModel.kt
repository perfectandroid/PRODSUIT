package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.PickUpDeliveryUpdateDetailsModel
import com.perfect.prodsuit.Repository.PickUpDeliveryUpdateDetailsRepository

class PickUpDeliveryUpdateDetailsViewModel : ViewModel(){

    var PickUpDeliveryUpdateDetailsData: MutableLiveData<PickUpDeliveryUpdateDetailsModel>? = null

    fun getPickUpDeliveryUpdateDetails(context: Context,SubMode: String, ID_ProductDelivery: String, TransMode: String) : LiveData<PickUpDeliveryUpdateDetailsModel>? {
        PickUpDeliveryUpdateDetailsData = PickUpDeliveryUpdateDetailsRepository.getServicesApiCall(context,SubMode,ID_ProductDelivery,TransMode)
        return PickUpDeliveryUpdateDetailsData
    }
}