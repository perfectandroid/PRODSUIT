package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadCountFollowupModel
import com.perfect.prodsuit.Model.LeadTileModel
import com.perfect.prodsuit.Model.ServiceCountModel
import com.perfect.prodsuit.Repository.LeadCountFollowupRepository
import com.perfect.prodsuit.Repository.LeadTileRepository
import com.perfect.prodsuit.Repository.ServiceCountRepository

class LeadCountFollowupViewModel : ViewModel() {

    var leadCountfollowupData: MutableLiveData<LeadCountFollowupModel>? = null

    fun getLeadCountFollowupCount(context: Context) : LiveData<LeadCountFollowupModel>? {
        leadCountfollowupData = LeadCountFollowupRepository.getServicesApiCall(context)
        return leadCountfollowupData
    }
}