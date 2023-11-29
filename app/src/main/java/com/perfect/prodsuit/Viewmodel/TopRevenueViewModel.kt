package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadStagesDashModel
import com.perfect.prodsuit.Model.LeadStatusDashModel
import com.perfect.prodsuit.Model.LeadstagewiseModel
import com.perfect.prodsuit.Model.TopRevenueModel
import com.perfect.prodsuit.Repository.LeadStagesForecastRepository
import com.perfect.prodsuit.Repository.LeadStatusDashRepository
import com.perfect.prodsuit.Repository.TopRevenueRepository

class TopRevenueViewModel : ViewModel() {

    var topRevenueData: MutableLiveData<TopRevenueModel>? = null
    fun getRevenue(context: Context) : LiveData<TopRevenueModel>? {
        topRevenueData = TopRevenueRepository.getServicesApiCall(context)
        return topRevenueData
    }
}