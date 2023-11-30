package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.TotalStagewiseDueModel
import com.perfect.prodsuit.Repository.TotalStagewiseDueRespository

class TotalStagewiseDueBarViewModel : ViewModel() {

    var totalstagewiseDueBarData: MutableLiveData<TotalStagewiseDueModel>? = null
    fun getTotalStagewiseDue(context: Context,TransDate: String) : LiveData<TotalStagewiseDueModel>? {
        totalstagewiseDueBarData = TotalStagewiseDueRespository.getServicesApiCall(context,TransDate)
        return totalstagewiseDueBarData
    }
}