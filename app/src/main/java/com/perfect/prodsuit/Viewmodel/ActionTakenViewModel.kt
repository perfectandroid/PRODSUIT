package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ActionTakenActionModel
import com.perfect.prodsuit.Repository.ActionTakenActionRepository

class ActionTakenViewModel : ViewModel() {

    var actionTakenActionLiveData: MutableLiveData<ActionTakenActionModel>? = null

    fun getActionTakenAction(context: Context,ID_Product: String, ID_Category: String) : LiveData<ActionTakenActionModel>? {
        actionTakenActionLiveData = ActionTakenActionRepository.getServicesApiCall(context,ID_Product,ID_Category)
        return actionTakenActionLiveData
    }

}