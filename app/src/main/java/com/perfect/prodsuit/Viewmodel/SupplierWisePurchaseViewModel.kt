package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.SupplierWisePurchaseModel
import com.perfect.prodsuit.Repository.SupplierWisePurchaseRepository

class SupplierWisePurchaseViewModel  : ViewModel(){

    var supplierwisePurchaseData: MutableLiveData<SupplierWisePurchaseModel>? = null

    fun getSupplierWisePurchase(context: Context,currentDate: String,DashMode: String,DashType: String) : LiveData<SupplierWisePurchaseModel>? {
        supplierwisePurchaseData = SupplierWisePurchaseRepository.getServicesApiCall(context,currentDate,DashMode,DashType)
        return supplierwisePurchaseData
    }
}