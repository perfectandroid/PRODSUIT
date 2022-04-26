package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.StateModel
import com.perfect.prodsuit.Repository.StateRepository

class StateViewModel : ViewModel() {

    var stateLiveData: MutableLiveData<StateModel>? = null

    fun getState(context: Context,FK_Country: String) : LiveData<StateModel>? {
        stateLiveData = StateRepository.getServicesApiCall(context,FK_Country)
        return stateLiveData
    }
}