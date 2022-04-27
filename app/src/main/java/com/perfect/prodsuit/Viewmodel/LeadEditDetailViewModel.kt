package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadEditDetailModel
import com.perfect.prodsuit.Repository.LeadEditDetailRepository

class LeadEditDetailViewModel : ViewModel()  {

    var leadEditDetData: MutableLiveData<LeadEditDetailModel>? = null

    fun getLeadEditDetail(context: Context) : LiveData<LeadEditDetailModel>? {
        leadEditDetData = LeadEditDetailRepository.getServicesApiCall(context)
        return leadEditDetData
    }
}