package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.StockReqProductlistModel
import com.perfect.prodsuit.Repository.BranchRepository
import com.perfect.prodsuit.Repository.StockReqProductListRepository

class StockReqProductlistViewModel  : ViewModel() {

    var stockReqProductlistTData: MutableLiveData<StockReqProductlistModel>? = null
    fun getStockReqProductlist(context: Context, ID_StockTransfer : String,TransMode : String,Detailed : String) : LiveData<StockReqProductlistModel>? {
        stockReqProductlistTData = StockReqProductListRepository.getServicesApiCall(context,ID_StockTransfer,TransMode,Detailed)
        return stockReqProductlistTData
    }

}