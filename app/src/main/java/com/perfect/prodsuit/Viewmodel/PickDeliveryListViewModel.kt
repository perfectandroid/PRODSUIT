package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.PickDeliveryListModel
import com.perfect.prodsuit.Repository.PickDeliveriListRepository

class PickDeliveryListViewModel  : ViewModel() {

    var PickDeliveryListData: MutableLiveData<PickDeliveryListModel>? = null

    fun getPickDeliveryList(context: Context,SubMode: String,strCustomer: String,strFromDate: String,strToDate: String,strMobile: String,strProduct: String,strTicketNo: String,FK_Area: String,status_id: String) : LiveData<PickDeliveryListModel>? {
        PickDeliveryListData = PickDeliveriListRepository.getServicesApiCall(context,SubMode,strCustomer,strFromDate,strToDate,strMobile,strProduct,strTicketNo,FK_Area,status_id)
        return PickDeliveryListData
    }
}