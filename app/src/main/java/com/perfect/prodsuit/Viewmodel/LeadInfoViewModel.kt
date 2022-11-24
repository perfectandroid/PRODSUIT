package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadInfoModel
import com.perfect.prodsuit.Repository.LeadInfoRepository

class LeadInfoViewModel: ViewModel() {

    var leadInfoData: MutableLiveData<LeadInfoModel>? = null

    fun getLeadInfo(context: Context,ID_LeadGenerateProduct : String) : LiveData<LeadInfoModel>? {
        leadInfoData = LeadInfoRepository.getServicesApiCall(context,ID_LeadGenerateProduct)
        return leadInfoData
    }

}