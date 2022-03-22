package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.SetMpinModel
import com.perfect.prodsuit.Reprository.SetMpinActivityRepository

class SetMpinActivityViewModel : ViewModel() {

    var setmpinLiveData: MutableLiveData<SetMpinModel>? = null

    fun setMpin(context: Context) : LiveData<SetMpinModel>? {
        setmpinLiveData = SetMpinActivityRepository.getServicesApiCall(context)
        return setmpinLiveData
    }

}