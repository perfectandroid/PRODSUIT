package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.DeleteStockModel
import com.perfect.prodsuit.Repository.DeleteStockRepository


class DeleteStockViewModel : ViewModel() {

    var deleteStockData: MutableLiveData<DeleteStockModel>? = null

    fun deleteStock(context: Context,FK_StockTransfer : String, TransMode : String, Reason : String) : LiveData<DeleteStockModel>? {
        deleteStockData = DeleteStockRepository.getServicesApiCall(context,FK_StockTransfer,TransMode,Reason)
        return deleteStockData
    }
}