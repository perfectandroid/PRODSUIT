package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadOutstandTileModel
import com.perfect.prodsuit.Model.LeadTileModel
import com.perfect.prodsuit.Model.ServiceCountModel
import com.perfect.prodsuit.Repository.LeadOutstandTileRepository
import com.perfect.prodsuit.Repository.LeadTileRepository
import com.perfect.prodsuit.Repository.ServiceCountRepository

class LeadOutstandViewModel : ViewModel() {

    var leadOutstandTileCountData: MutableLiveData<LeadOutstandTileModel>? = null

    fun getLeadOutstandTileCount(context: Context) : LiveData<LeadOutstandTileModel>? {
        leadOutstandTileCountData = LeadOutstandTileRepository.getServicesApiCall(context)
        return leadOutstandTileCountData
    }
}