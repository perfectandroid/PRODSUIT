package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadEditDetailModel
import com.perfect.prodsuit.Repository.LeadEditDetailRepository

class LeadEditDetailViewModel : ViewModel() {

    var leadEditData: MutableLiveData<LeadEditDetailModel>? = null

    fun getLeadEditDetails(context: Context) : LiveData<LeadEditDetailModel>? {
        leadEditData = LeadEditDetailRepository.getServicesApiCall(context)
        return leadEditData
    }
}