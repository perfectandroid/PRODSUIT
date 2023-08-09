package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ReasonModel
import com.perfect.prodsuit.Repository.ReasonRepository

class ReasonViewModel : ViewModel() {

    var reasonData: MutableLiveData<ReasonModel>? = null

    fun getReason(context: Context) : LiveData<ReasonModel>? {
        reasonData = ReasonRepository.getServicesApiCall(context)
        return reasonData
    }
}