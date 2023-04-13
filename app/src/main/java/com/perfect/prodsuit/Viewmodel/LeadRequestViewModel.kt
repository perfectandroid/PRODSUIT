package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadRequestModel
import com.perfect.prodsuit.Repository.LeadRequestRepository

class LeadRequestViewModel : ViewModel()  {

    var leadRequestData: MutableLiveData<LeadRequestModel>? = null

    fun getLeadRequest(context: Context) : MutableLiveData<LeadRequestModel>? {
        leadRequestData = LeadRequestRepository.getServicesApiCall(context)
        return leadRequestData
    }
}