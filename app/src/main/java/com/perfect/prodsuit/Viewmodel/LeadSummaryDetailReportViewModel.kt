package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadSummaryDetailsReportModel
import com.perfect.prodsuit.Repository.LeadSummaryReportRepository

class LeadSummaryDetailReportViewModel  : ViewModel() {

    var summaryreportData: MutableLiveData<LeadSummaryDetailsReportModel>? = null

    fun getLeadSummaryDetailReport(
        context: Context,
        submode: String?,
        strFromdate: String?,
        strTodate: String?,
        ID_Product: String?,
        ID_Category: String?,
        ID_Branch: String?,
        ID_Employee: String?,
        ID_AsgndEmployee: String?
    ) : LiveData<LeadSummaryDetailsReportModel>? {
        summaryreportData = LeadSummaryReportRepository.getServicesApiCall(context,submode!!,
            strFromdate!!, strTodate!!, ID_Product!!, ID_Category!!, ID_Branch!!, ID_Employee!!,ID_AsgndEmployee)
        return summaryreportData
    }
}