package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadDashModel
import com.perfect.prodsuit.Repository.LeadDashRepository

class LeadDashViewModel : ViewModel(){

    var leaddashData: MutableLiveData<LeadDashModel>? = null
    fun getLeadDashboard(context: Context) : LiveData<LeadDashModel>? {
        leaddashData = LeadDashRepository.getServicesApiCall(context)
        return leaddashData
    }
}