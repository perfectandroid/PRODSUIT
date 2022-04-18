package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.MaintananceMessageModel
import com.perfect.prodsuit.Repository.MaintanaceMessageRepository

class MaintanceMessageViewModel : ViewModel() {

    var maintanaceLiveData: MutableLiveData<MaintananceMessageModel>? = null

    fun getMaintanceMessgae(context: Context) : LiveData<MaintananceMessageModel>? {
        maintanaceLiveData = MaintanaceMessageRepository.getServicesApiCall(context)
        return maintanaceLiveData
    }

}