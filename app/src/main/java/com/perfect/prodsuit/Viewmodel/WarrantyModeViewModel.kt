package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.WarrantyModeModel
import com.perfect.prodsuit.Repository.WarrantyModeRepository

class WarrantyModeViewModel : ViewModel() {

    var warrantymodeListtData: MutableLiveData<WarrantyModeModel>? = null
    fun getWarrantyMode(context: Context,WarrantyMode: String) : MutableLiveData<WarrantyModeModel>? {

        warrantymodeListtData = WarrantyModeRepository.getServicesApiCall(context,WarrantyMode)
        return warrantymodeListtData
    }
}