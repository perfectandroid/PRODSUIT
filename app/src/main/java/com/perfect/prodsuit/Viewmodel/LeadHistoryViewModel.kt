package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadHistoryModel
import com.perfect.prodsuit.Repository.LeadHistoryRepository

class LeadHistoryViewModel: ViewModel() {
    var leadHistoryData: MutableLiveData<LeadHistoryModel>? = null
    fun getLeadHistory(context: Context) : LiveData<LeadHistoryModel>? {
        leadHistoryData = LeadHistoryRepository.getServicesApiCall(context)
        return leadHistoryData
    }
}