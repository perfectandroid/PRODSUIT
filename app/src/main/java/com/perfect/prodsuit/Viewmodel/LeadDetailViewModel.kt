package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadDetailModel
import com.perfect.prodsuit.Repository.LeadDetailRepository

class LeadDetailViewModel  : ViewModel() {

    var LeadDetailData: MutableLiveData<LeadDetailModel>? = null

    fun getLeadDetail(context: Context ) : LiveData<LeadDetailModel>? {
        LeadDetailData = LeadDetailRepository.getServicesApiCall(context)
        return LeadDetailData
    }
}