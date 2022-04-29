package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadStatusDashModel
import com.perfect.prodsuit.Repository.LeadStatusDashRepository

class LeadStatusDashViewModel : ViewModel() {

    var leadstatusdashData: MutableLiveData<LeadStatusDashModel>? = null
    fun getLeadStatusDashboard(context: Context) : LiveData<LeadStatusDashModel>? {
        leadstatusdashData = LeadStatusDashRepository.getServicesApiCall(context)
        return leadstatusdashData
    }
}