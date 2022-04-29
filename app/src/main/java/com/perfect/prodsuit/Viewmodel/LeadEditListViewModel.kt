package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadEditListModel
import com.perfect.prodsuit.Repository.LeadEditListRepository

class LeadEditListViewModel : ViewModel() {

    var leadEditData: MutableLiveData<LeadEditListModel>? = null

    fun getLeadEditList(context: Context) : LiveData<LeadEditListModel>? {
        leadEditData = LeadEditListRepository.getServicesApiCall(context)
        return leadEditData
    }
}