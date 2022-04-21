package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.StateModel
import com.perfect.prodsuit.Repository.StateRepository

class StateViewModel : ViewModel() {

    var stateLiveData: MutableLiveData<StateModel>? = null

    fun getState(context: Context) : LiveData<StateModel>? {
        stateLiveData = StateRepository.getServicesApiCall(context)
        return stateLiveData
    }
}