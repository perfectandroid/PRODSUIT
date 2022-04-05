package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadThroughModel
import com.perfect.prodsuit.Repository.LeadThroughRepository

class LeadThroughViewModel : ViewModel() {

    var leadThroughData: MutableLiveData<LeadThroughModel>? = null

    fun getLeadThrough(context: Context) : MutableLiveData<LeadThroughModel>? {
        leadThroughData = LeadThroughRepository.getServicesApiCall(context)
        return leadThroughData
    }
}