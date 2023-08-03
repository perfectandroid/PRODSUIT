package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.StockRTListModel
import com.perfect.prodsuit.Repository.StockRTListRepository

class StockRTListViewModel: ViewModel() {

    var stockRTListData: MutableLiveData<StockRTListModel>? = null

    fun getStockRTList(context: Context, ID_Category : String, ID_Product : String) : LiveData<StockRTListModel>? {
        stockRTListData = StockRTListRepository.getServicesApiCall(context,ID_Category,ID_Product)
        return stockRTListData
    }

}