package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.StockModeModel
import com.perfect.prodsuit.Repository.StockModeRepository

class StockModeViewModel : ViewModel() {

    var stockModeData: MutableLiveData<StockModeModel>? = null

    fun getStockMode(context: Context) : LiveData<StockModeModel>? {
        stockModeData = StockModeRepository.getServicesApiCall(context)
        return stockModeData
    }
}