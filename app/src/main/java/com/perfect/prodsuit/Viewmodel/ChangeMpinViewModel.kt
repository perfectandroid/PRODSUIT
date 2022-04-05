package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ChangeMpinModel
import com.perfect.prodsuit.Repository.ChangeMpinRepository

class ChangeMpinViewModel : ViewModel() {

    var changempinLiveData: MutableLiveData<ChangeMpinModel>? = null

    fun changeMpin(context: Context) : LiveData<ChangeMpinModel>? {
        changempinLiveData = ChangeMpinRepository.getServicesApiCall(context)
        return changempinLiveData
    }

}