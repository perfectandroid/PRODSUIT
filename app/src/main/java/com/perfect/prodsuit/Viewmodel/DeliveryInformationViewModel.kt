package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.DeliveryInformationModel
import com.perfect.prodsuit.Repository.DeliveryInformationRepository

class DeliveryInformationViewModel: ViewModel() {
    var deliveryInformationData: MutableLiveData<DeliveryInformationModel>? = null

    fun getDeliveryInformation(context: Context,SubMode: String, ID_ProductDelivery: String, TransMode: String) : MutableLiveData<DeliveryInformationModel>? {
        deliveryInformationData = DeliveryInformationRepository.getServicesApiCall(context,SubMode,ID_ProductDelivery,TransMode)
        return deliveryInformationData
    }

}