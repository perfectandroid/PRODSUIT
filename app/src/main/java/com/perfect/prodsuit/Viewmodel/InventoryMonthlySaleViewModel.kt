package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.InventoryMonthlySaleModel
import com.perfect.prodsuit.Repository.InventoryMonthlySaleRepository

class InventoryMonthlySaleViewModel : ViewModel() {

    var inventorySaleData: MutableLiveData<InventoryMonthlySaleModel>? = null

    fun getInventoryMothlySale(context: Context) : LiveData<InventoryMonthlySaleModel>? {
        inventorySaleData = InventoryMonthlySaleRepository.getServicesApiCall(context)
        return inventorySaleData
    }
}