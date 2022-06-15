package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadManagmntFilterModel
import com.perfect.prodsuit.Model.OverDueModel
import com.perfect.prodsuit.Repository.LeadManagFilterRepository
import com.perfect.prodsuit.Repository.OverDueRepository

class LeadMangeFilterViewModel : ViewModel() {

    var leanmangfilterLiveData: MutableLiveData<LeadManagmntFilterModel>? = null

    fun getLeadMangfilter(context: Context) : LiveData<LeadManagmntFilterModel>? {
        leanmangfilterLiveData = LeadManagFilterRepository.getServicesApiCall(context)
        return leanmangfilterLiveData
    }

}