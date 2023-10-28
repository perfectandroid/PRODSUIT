package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.OtherActionModel
import com.perfect.prodsuit.Repository.OtherChargesRepository

class OtherChargesViewViewModel : ViewModel() {

    var actionTakenActionLiveData: MutableLiveData<OtherActionModel>? = null

    fun getActionTakenAction(context: Context) : LiveData<OtherActionModel>? {
        actionTakenActionLiveData = OtherChargesRepository.getServicesApiCall(context)
        return actionTakenActionLiveData
    }

}