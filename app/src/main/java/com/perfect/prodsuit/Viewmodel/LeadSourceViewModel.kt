package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadSourceModel

import com.perfect.prodsuit.Repository.LeadFromRepository
import com.perfect.prodsuit.Repository.LeadSourceRepository

class LeadSourceViewModel : ViewModel() {

    var leadSource: MutableLiveData<LeadSourceModel>? = null

    fun getLeadSource(context: Context) : MutableLiveData<LeadSourceModel>? {
        leadSource = LeadSourceRepository.getServicesApiCall(context)
        return leadSource
    }

}