package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.SentIntimationModel
import com.perfect.prodsuit.Repository.SentIntimationRepository
import org.json.JSONArray

class SentIntimationViewModel: ViewModel() {

    var sentData: MutableLiveData<SentIntimationModel>? = null

    fun sentIntimation(
        context: Context,
        dated: String,
        ID_module: String,
        ID_Branch: String,
        ID_Channel: String,
        ID_Shedule: String,
        encodeDoc: String,
        extension: String,
        message: String,
        ID_LeadSource: String,
        ID_LeadInfo: String,
        FromDate: String,
        ToDate: String,
        ID_Category: String?,
        ID_ProductType: String?,
        ID_Product: String,
        ID_Employee: String,
        ID_CollectedBy: String?,
        idArea: String,
        ID_NextAction: String,
        ID_ActionType: String,
        ID_Priority: String?,
        SearchBy: String,
        SearchBydetails: String,
        GridData: String,
        LeadCusDetails: JSONArray
    ) : LiveData<SentIntimationModel>? {
        sentData = SentIntimationRepository.getServicesApiCall(
            context,dated,ID_module,ID_Branch,ID_Channel,ID_Shedule,encodeDoc,extension,message,
            ID_LeadSource,
            ID_LeadInfo,
            FromDate,
            ToDate,
            ID_Category,
            ID_ProductType,
            ID_Product,
            ID_Employee,
            ID_CollectedBy,
            idArea,
            ID_NextAction,
            ID_ActionType,
            ID_Priority,
            SearchBy,
            SearchBydetails,
            GridData,
            LeadCusDetails
        )
        return sentData
    }

}