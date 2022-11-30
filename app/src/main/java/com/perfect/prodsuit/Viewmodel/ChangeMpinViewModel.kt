package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ChangeMpinModel
import com.perfect.prodsuit.Repository.ChangeMpinRepository
import com.perfect.prodsuit.View.Activity.HomeActivity

class ChangeMpinViewModel : ViewModel() {

    var changempinLiveData: MutableLiveData<ChangeMpinModel>? = null

    fun changeMpin(context: Context,strOldMPIN : String, strNewMPIN : String) : LiveData<ChangeMpinModel>? {
        changempinLiveData = ChangeMpinRepository.getServicesApiCall(context, strOldMPIN, strNewMPIN)
        return changempinLiveData
    }

}