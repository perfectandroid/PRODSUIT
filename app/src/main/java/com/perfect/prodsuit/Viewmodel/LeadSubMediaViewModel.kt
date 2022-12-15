package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadSubMediaModel
import com.perfect.prodsuit.Repository.LeadSubMediaRepository
import com.perfect.prodsuit.View.Activity.LeadGenerationActivity

class LeadSubMediaViewModel : ViewModel()  {

    var LeadSubMediaData: MutableLiveData<LeadSubMediaModel>? = null
    fun getLeadSubMedia(context: Context, ID_LeadThrough : String) : MutableLiveData<LeadSubMediaModel>? {
        LeadSubMediaData = LeadSubMediaRepository.getServicesApiCall(context, ID_LeadThrough
        )
        return LeadSubMediaData
    }
}