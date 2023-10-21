package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ActionListTicketReportModel
import com.perfect.prodsuit.Model.WarrantyAMCModel
import com.perfect.prodsuit.Repository.ActionListTicketReportRepository
import com.perfect.prodsuit.Repository.WarrantyAMCRepository

class WarrantyAMCViewModel : ViewModel() {

    var warrantyListtData: MutableLiveData<WarrantyAMCModel>? = null
    fun getWarrantyAMC(context: Context) : MutableLiveData<WarrantyAMCModel>? {
        warrantyListtData = WarrantyAMCRepository.getServicesApiCall(context)
        return warrantyListtData
    }
}