package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.TopSellingItemModel
import com.perfect.prodsuit.Repository.TopSellingItemRepository


class TopSellingViewModel : ViewModel() {

    var topSellingListData: MutableLiveData<TopSellingItemModel>? = null

    fun getTopSellingCategory(context: Context) : LiveData<TopSellingItemModel>? {
        topSellingListData = TopSellingItemRepository.getServicesApiCall(context)
        return topSellingListData
    }
}