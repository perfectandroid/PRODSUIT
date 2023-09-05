package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadNoModel
import com.perfect.prodsuit.Model.MeasurementTypeModel
import com.perfect.prodsuit.Repository.LeadNoRepository
import com.perfect.prodsuit.Repository.MeasurementTypeRepository

class LeadNoViewModel : ViewModel() {

    var leadnoData: MutableLiveData<LeadNoModel>? = null

    fun getLeadNo(context: Context) : LiveData<LeadNoModel>? {
        leadnoData = LeadNoRepository.getServicesApiCall(context)
        return leadnoData
    }
}