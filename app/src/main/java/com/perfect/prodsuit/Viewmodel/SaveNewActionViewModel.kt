package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.SaveNewActionModel
import com.perfect.prodsuit.Repository.SaveNewActionRepository

class SaveNewActionViewModel: ViewModel() {

    var saveNewactionData: MutableLiveData<SaveNewActionModel>? = null
    fun saveNewAction(context: Context, ID_Category : String,ID_Product : String, ID_NextAction : String, ID_ActionType : String,
                      strDate : String, ID_Department : String, ID_Employee : String) : LiveData<SaveNewActionModel>? {
        saveNewactionData = SaveNewActionRepository.getServicesApiCall(context, ID_Category, ID_Product,ID_NextAction, ID_ActionType,
            strDate, ID_Department, ID_Employee!!)
        return saveNewactionData
    }
}