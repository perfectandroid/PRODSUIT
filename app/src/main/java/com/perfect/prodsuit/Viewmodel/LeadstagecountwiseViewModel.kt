package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.*
import com.perfect.prodsuit.Repository.*


class LeadstagecountwiseViewModel : ViewModel() {

    var leadstagecountwiseData: MutableLiveData<leadstagecountwiseModel>? = null

    fun getleadstagecountwise(context: Context) : LiveData<leadstagecountwiseModel>? {
        leadstagecountwiseData = leadstagecountwiseRepository.getServicesApiCall(context)
        return leadstagecountwiseData
    }
}