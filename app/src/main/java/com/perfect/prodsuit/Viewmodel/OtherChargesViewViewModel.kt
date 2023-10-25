package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ActionTakenActionModel
import com.perfect.prodsuit.Model.OtherActionModel
import com.perfect.prodsuit.Model.ProductWiseComplaintModel
import com.perfect.prodsuit.Repository.ActionTakenActionRepository
import com.perfect.prodsuit.Repository.OtherChargesRepository
import com.perfect.prodsuit.Repository.ProductWiseComplaintRepository

class OtherChargesViewViewModel : ViewModel() {

    var actionTakenActionLiveData: MutableLiveData<OtherActionModel>? = null

    fun getActionTakenAction(context: Context) : LiveData<OtherActionModel>? {
        actionTakenActionLiveData = OtherChargesRepository.getServicesApiCall(context)
        return actionTakenActionLiveData
    }

}