package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ActionTakenActionModel
import com.perfect.prodsuit.Model.LeadActionActionModel
import com.perfect.prodsuit.Model.ProductWiseComplaintModel
import com.perfect.prodsuit.Repository.ActionTakenActionRepository
import com.perfect.prodsuit.Repository.LeadActionRepository
import com.perfect.prodsuit.Repository.ProductWiseComplaintRepository

class LeadActionViewModel : ViewModel() {

    var actionTakenActionLiveData: MutableLiveData<LeadActionActionModel>? = null

    fun getActionTakenAction(context: Context,ID_Product: String, ID_Category: String) : LiveData<LeadActionActionModel>? {
        actionTakenActionLiveData = LeadActionRepository.getServicesApiCall(context)
        return actionTakenActionLiveData
    }

}