package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.MpinModel
import com.perfect.prodsuit.Repository.MpinActivityRepository
import com.perfect.prodsuit.View.Activity.MpinActivity

class MpinActivityViewModel : ViewModel() {

    var mpinLiveData: MutableLiveData<MpinModel>? = null

    fun veryfyMpin(context: Context,strMPIN :  String) : LiveData<MpinModel>? {
        mpinLiveData = MpinActivityRepository.getServicesApiCall(context, strMPIN)
        return mpinLiveData
    }

}