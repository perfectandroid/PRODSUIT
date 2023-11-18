package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.StockListCategoryModel
import com.perfect.prodsuit.Repository.StockListCategoryRepository


class StockListViewModel : ViewModel() {

    var stockListData: MutableLiveData<StockListCategoryModel>? = null

    fun getStockListCategory(context: Context) : LiveData<StockListCategoryModel>? {
        stockListData = StockListCategoryRepository.getServicesApiCall(context)
        return stockListData
    }
}