package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.TopSupplierListModel
import com.perfect.prodsuit.Repository.TopSupplierListRepository


class TopSupplierViewModel : ViewModel() {

    var topSupplierListData: MutableLiveData<TopSupplierListModel>? = null

    fun getTopSupplierCategory(context: Context) : LiveData<TopSupplierListModel>? {
        topSupplierListData = TopSupplierListRepository.getServicesApiCall(context)
        return topSupplierListData
    }
}