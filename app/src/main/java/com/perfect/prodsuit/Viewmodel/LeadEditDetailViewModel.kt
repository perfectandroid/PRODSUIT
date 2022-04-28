package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadEditDetailModel
import com.perfect.prodsuit.Repository.LeadEditDetailRepository
import com.perfect.prodsuit.View.Activity.LeadGenerationActivity

class LeadEditDetailViewModel : ViewModel()  {

    var leadEditDetData: MutableLiveData<LeadEditDetailModel>? = null

    fun getLeadEditDetail(context: Context,ID_LeadGenerate : String,ID_LeadGenerateProduct  :String) : LiveData<LeadEditDetailModel>? {
        leadEditDetData = LeadEditDetailRepository.getServicesApiCall(context, ID_LeadGenerate, ID_LeadGenerateProduct)
        return leadEditDetData
    }
}