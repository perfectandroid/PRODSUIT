package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ItemSearchListModel
import com.perfect.prodsuit.Repository.ItemSearchListRepository

class ItemSearchListViewModel : ViewModel() {

    var itemSearchListData: MutableLiveData<ItemSearchListModel>? = null

    fun getProductEnquiry(context: Context, ReqMode : String, strBarcode : String) : LiveData<ItemSearchListModel>? {
        itemSearchListData = ItemSearchListRepository.getServicesApiCall(context,ReqMode,strBarcode)
        return itemSearchListData
    }
}