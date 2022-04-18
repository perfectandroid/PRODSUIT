package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadByModel
import com.perfect.prodsuit.Repository.LeadByRepository

class LeadByViewModel : ViewModel()  {

    var leadByData: MutableLiveData<LeadByModel>? = null

    fun getLeadBy(context: Context) : MutableLiveData<LeadByModel>? {
        leadByData = LeadByRepository.getServicesApiCall(context)
        return leadByData
    }

}