package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.DashBoardCountModel
import com.perfect.prodsuit.Repository.DashBoardCountRepository

class DashBoardCountViewModel : ViewModel() {

    var dashboardCountData: MutableLiveData<DashBoardCountModel>? = null
    fun getDashBoardCount(context: Context) : MutableLiveData<DashBoardCountModel>? {
        dashboardCountData = DashBoardCountRepository.getDashBoardCountApiCall(context)
        return dashboardCountData
    }
}