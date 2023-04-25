package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.PickupDeliveryModel
import com.perfect.prodsuit.Repository.PickupDeliveryRepository

class PickupDeliveryCountsViewModel : ViewModel() {

    var pickupdeliveryCountData: MutableLiveData<PickupDeliveryModel>? = null

    fun getPickupDeliveryCounts(context: Context, ID_Employee: String, FK_Area: String,strFromDate: String, strToDate: String, strarea: String, strCustomer: String, strMobile: String, stProduct: String, strTicketNo: String,status_id: String) : MutableLiveData<PickupDeliveryModel>? {
        pickupdeliveryCountData = PickupDeliveryRepository.getServicesApiCall(context,ID_Employee,FK_Area,strFromDate,strToDate,strarea,strCustomer,strMobile,stProduct,strTicketNo,status_id)
        return pickupdeliveryCountData
    }
}