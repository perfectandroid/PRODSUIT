package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.SalesComparisonModel
import com.perfect.prodsuit.Model.StockValueDataModel

import com.perfect.prodsuit.Repository.SalesComparisonRepository
import com.perfect.prodsuit.Repository.StockValueDataRepository


class StockValueDataViewModel : ViewModel() {

    var stockValueLiveData: MutableLiveData<StockValueDataModel>? = null

    fun getStockValueData(context: Context) : LiveData<StockValueDataModel>? {
        stockValueLiveData = StockValueDataRepository.getServicesApiCall(context)
        return stockValueLiveData
    }
}