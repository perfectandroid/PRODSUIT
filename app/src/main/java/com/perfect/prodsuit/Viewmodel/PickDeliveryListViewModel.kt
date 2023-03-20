package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.PickDeliveryListModel
import com.perfect.prodsuit.Repository.PickDeliveriListRepository

class PickDeliveryListViewModel  : ViewModel() {

    var PickDeliveryListData: MutableLiveData<PickDeliveryListModel>? = null

    fun getPickDeliveryList(context: Context) : LiveData<PickDeliveryListModel>? {
        PickDeliveryListData = PickDeliveriListRepository.getServicesApiCall(context)
        return PickDeliveryListData
    }
}