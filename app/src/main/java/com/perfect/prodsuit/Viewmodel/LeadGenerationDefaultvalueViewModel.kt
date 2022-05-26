package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadGenerationDefaultvalueModel
import com.perfect.prodsuit.Repository.LeadGenerationDefaultvalueRepository

class LeadGenerationDefaultvalueViewModel : ViewModel() {

    var leadGenerationDefaultvalueLiveData: MutableLiveData<LeadGenerationDefaultvalueModel>? = null

    fun getLeadGenerationDefaultvalue(context: Context) : LiveData<LeadGenerationDefaultvalueModel>? {
        leadGenerationDefaultvalueLiveData = LeadGenerationDefaultvalueRepository.getServicesApiCall(context)
        return leadGenerationDefaultvalueLiveData
    }

}