package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.StockRequestModel
import com.perfect.prodsuit.Repository.StockRequestRepository

class StockRequestViewModel  : ViewModel() {

    var stockRequestData: MutableLiveData<StockRequestModel>? = null

    fun getStockRequest(context: Context) : LiveData<StockRequestModel>? {
        stockRequestData = StockRequestRepository.getServicesApiCall(context)
        return stockRequestData
    }
}