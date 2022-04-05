package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.MpinModel
import com.perfect.prodsuit.Repository.MpinActivityRepository

class MpinActivityViewModel : ViewModel() {

    var mpinLiveData: MutableLiveData<MpinModel>? = null

    fun veryfyMpin(context: Context) : LiveData<MpinModel>? {
        mpinLiveData = MpinActivityRepository.getServicesApiCall(context)
        return mpinLiveData
    }

}