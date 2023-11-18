package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.InventoryMonthlySaleModel
import com.perfect.prodsuit.Model.LeadOutstandTileModel
import com.perfect.prodsuit.Model.LeadTileModel
import com.perfect.prodsuit.Model.ServiceCountModel
import com.perfect.prodsuit.Repository.InventoryMonthlySaleRepository
import com.perfect.prodsuit.Repository.LeadOutstandTileRepository
import com.perfect.prodsuit.Repository.LeadTileRepository
import com.perfect.prodsuit.Repository.ServiceCountRepository

class InventoryMonthlySaleViewModel : ViewModel() {

    var inventorySaleData: MutableLiveData<InventoryMonthlySaleModel>? = null

    fun getInventoryMothlySale(context: Context) : LiveData<InventoryMonthlySaleModel>? {
        inventorySaleData = InventoryMonthlySaleRepository.getServicesApiCall(context)
        return inventorySaleData
    }
}