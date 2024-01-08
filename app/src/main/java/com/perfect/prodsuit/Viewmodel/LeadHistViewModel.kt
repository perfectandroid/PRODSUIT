package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadHistModel

import com.perfect.prodsuit.Repository.LeadHistRepository

class LeadHistViewModel  : ViewModel() {

    var LeadDetailData: MutableLiveData<LeadHistModel>? = null

    fun getLeadHist(
        context: Context,
        ID_LeadSource: String,
        ID_LeadInfo: String,
        fromDate1: String,
        toDate1: String,
        ID_Category: String?,
        ID_ProductType: String?,
        ID_Product: String,
        ID_Employee: String,
        ID_CollectedBy: String?,
        ID_Area: String?,
        ID_NextAction: String,
        ID_ActionType: String,
        ID_Priority: String?,
        ID_SearchBy: String?,
        searchDetails: String?,
        transmode: String?,
        prodName: String
    ) : LiveData<LeadHistModel>? {
        LeadDetailData = LeadHistRepository.getServicesApiCall(context,
            ID_LeadSource,
            ID_LeadInfo,fromDate1,
            toDate1,
            ID_Category,
            ID_ProductType,
            ID_Product,
            ID_Employee,
            ID_CollectedBy,
            ID_Area,
            ID_NextAction,
            ID_ActionType,
            ID_Priority,
            ID_SearchBy,
            searchDetails,
            transmode,
            prodName

            )
        return LeadDetailData
    }
}