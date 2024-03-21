package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadFromInfoModel
import com.perfect.prodsuit.Repository.LeadFromInfoRepository

class LeadFromInfoViewModel : ViewModel()  {

    var LeadFromInfoData: MutableLiveData<LeadFromInfoModel>? = null

    fun getLeadFromInfo(context: Context,ID_LeadFrom: String,TransMode: String) : LiveData<LeadFromInfoModel>? {
        LeadFromInfoData = LeadFromInfoRepository.getServicesApiCall(context,ID_LeadFrom,TransMode)
        return LeadFromInfoData
    }
}