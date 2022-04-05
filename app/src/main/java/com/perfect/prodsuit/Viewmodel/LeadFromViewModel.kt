package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadFromModel
import com.perfect.prodsuit.Repository.LeadFromRepository

class LeadFromViewModel : ViewModel() {

    var leadFromData: MutableLiveData<LeadFromModel>? = null

    fun getLeadFrom(context: Context) : MutableLiveData<LeadFromModel>? {
        leadFromData = LeadFromRepository.getServicesApiCall(context)
        return leadFromData
    }
}