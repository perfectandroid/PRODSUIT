package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.Top10ProjectModel
import com.perfect.prodsuit.Model.UpcomingStageDueDatesModel
import com.perfect.prodsuit.Repository.Top10ProjectRepository
import com.perfect.prodsuit.Repository.UpcomingStageDueDatesRespository

class UpcomingStageDueDatesViewModel : ViewModel() {

    var upcomingstageDueDatesData: MutableLiveData<UpcomingStageDueDatesModel>? = null
    fun getUpcomingStageDueDates(context: Context,TransDate: String) : LiveData<UpcomingStageDueDatesModel>? {
        upcomingstageDueDatesData = UpcomingStageDueDatesRespository.getServicesApiCall(context,TransDate)
        return upcomingstageDueDatesData
    }
}