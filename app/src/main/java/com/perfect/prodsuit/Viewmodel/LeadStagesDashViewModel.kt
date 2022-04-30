package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadStagesDashModel
import com.perfect.prodsuit.Repository.LeadStagesDashRepository

class LeadStagesDashViewModel : ViewModel() {

    var leadstagesdashData: MutableLiveData<LeadStagesDashModel>? = null
    fun getLeadStatusDashboard(context: Context) : LiveData<LeadStagesDashModel>? {
        leadstagesdashData = LeadStagesDashRepository.getServicesApiCall(context)
        return leadstagesdashData
    }
}