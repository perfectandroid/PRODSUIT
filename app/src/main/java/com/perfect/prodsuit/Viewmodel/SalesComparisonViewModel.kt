package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.SalesComparisonModel

import com.perfect.prodsuit.Repository.SalesComparisonRepository



class SalesComparisonViewModel : ViewModel() {

    var salesComparisonListData: MutableLiveData<SalesComparisonModel>? = null

    fun getSalesComparison(context: Context) : LiveData<SalesComparisonModel>? {
        salesComparisonListData = SalesComparisonRepository.getServicesApiCall(context)
        return salesComparisonListData
    }
}