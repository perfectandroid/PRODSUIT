package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadStagesDashModel
import com.perfect.prodsuit.Model.LeadStatusDashModel
import com.perfect.prodsuit.Model.LeadstagewiseModel
import com.perfect.prodsuit.Repository.LeadStagesForecastRepository
import com.perfect.prodsuit.Repository.LeadStatusDashRepository

class LeadStagesForecastDashViewModel : ViewModel() {

    var leadsttagesforecastdashData: MutableLiveData<LeadstagewiseModel>? = null
    fun getLeadStagesForecastDashboard(context: Context) : LiveData<LeadstagewiseModel>? {
        leadsttagesforecastdashData = LeadStagesForecastRepository.getServicesApiCall(context)
        return leadsttagesforecastdashData
    }
}