package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.Top10ProjectModel
import com.perfect.prodsuit.Repository.Top10ProjectRepository

class UpcomingStageDueDatesViewModel : ViewModel() {

    var upcomingstageDueDatesData: MutableLiveData<Top10ProjectModel>? = null
    fun getUpcomingStageDueDates(context: Context) : LiveData<Top10ProjectModel>? {
        upcomingstageDueDatesData = Top10ProjectRepository.getServicesApiCall(context)
        return upcomingstageDueDatesData
    }
}