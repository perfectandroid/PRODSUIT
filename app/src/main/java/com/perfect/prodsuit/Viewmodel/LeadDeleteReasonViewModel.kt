package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadDeleteReasonModel
import com.perfect.prodsuit.Repository.LeadDeleteReasonRepository

class LeadDeleteReasonViewModel  : ViewModel(){

    var leadDeletereasonData: MutableLiveData<LeadDeleteReasonModel>? = null

    fun getLeadDeleteReason(context: Context) : LiveData<LeadDeleteReasonModel>? {
        leadDeletereasonData = LeadDeleteReasonRepository.getServicesApiCall(context)
        return leadDeletereasonData
    }
}