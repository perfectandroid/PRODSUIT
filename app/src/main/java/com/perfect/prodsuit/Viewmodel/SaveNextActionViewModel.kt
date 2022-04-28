package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.SaveNextActionModel
import com.perfect.prodsuit.Repository.SaveNextActionRepository


class SaveNextActionViewModel: ViewModel() {
    var saveNextactionData: MutableLiveData<SaveNextActionModel>? = null
    fun saveNextAction(context: Context,ID_NextAction : String,ID_ActionType : String,strDate : String,
                       ID_Priority : String,ID_Department : String,ID_Employee : String) : LiveData<SaveNextActionModel>? {
        saveNextactionData = SaveNextActionRepository.getServicesApiCall(context, ID_NextAction, ID_ActionType, strDate,
            ID_Priority, ID_Department, ID_Employee)
        return saveNextactionData
    }

}