package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.leadActivityModel
import com.perfect.prodsuit.Repository.leadActivityRepository


class leadActivityViewModel : ViewModel() {

    var leadActivityData: MutableLiveData<leadActivityModel>? = null

    fun getleadActivity(context: Context) : LiveData<leadActivityModel>? {
        leadActivityData = leadActivityRepository.getServicesApiCall(context)
        return leadActivityData
    }
}