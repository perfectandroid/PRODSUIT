package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.DashboardReportModel
import com.perfect.prodsuit.Model.LeadAllDetailsModel
import com.perfect.prodsuit.Repository.DashboardReportRepository
import com.perfect.prodsuit.Repository.LeadAllDetailsRepository

class LeadAllDetailsViewModel : ViewModel() {

    var leadalldetailsdata: MutableLiveData<LeadAllDetailsModel>? = null

    fun getLeadAllDetails(context: Context,ID_Employee: String) : LiveData<LeadAllDetailsModel>? {
        leadalldetailsdata = LeadAllDetailsRepository.getServicesApiCall(context,ID_Employee)
        return leadalldetailsdata
    }

}