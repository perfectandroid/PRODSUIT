package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.UpcomingStockModel
import com.perfect.prodsuit.Repository.UpcomingStockRespository

class UpcomingStockViewModel : ViewModel() {

    var upcomingStockData: MutableLiveData<UpcomingStockModel>? = null

    fun getUpcomingStock(context: Context,TransDate: String) : LiveData<UpcomingStockModel>? {
        upcomingStockData = UpcomingStockRespository.getServicesApiCall(context,TransDate)
        return upcomingStockData
    }
}