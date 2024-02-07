package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.PunchReportModel
import com.perfect.prodsuit.Repository.PunchReportRepository

class PunchReportViewModel  : ViewModel() {

    var punchReprtData: MutableLiveData<PunchReportModel>? = null

    fun getPunchReprt(context: Context, strToDate: String?) : LiveData<PunchReportModel>? {
        punchReprtData = PunchReportRepository.getServicesApiCall(context,strToDate)
        return punchReprtData
    }
}