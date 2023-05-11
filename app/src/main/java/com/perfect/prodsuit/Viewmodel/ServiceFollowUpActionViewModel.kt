package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceFollowUpActionModel
import com.perfect.prodsuit.Repository.ServiceFollowUpActionRepository

class ServiceFollowUpActionViewModel: ViewModel()  {

    var followupactionData: MutableLiveData<ServiceFollowUpActionModel>? = null
    fun getServiceFollowupAction(context: Context) : LiveData<ServiceFollowUpActionModel>? {
        followupactionData = ServiceFollowUpActionRepository.getServicesApiCall(context)
        return followupactionData
    }
}