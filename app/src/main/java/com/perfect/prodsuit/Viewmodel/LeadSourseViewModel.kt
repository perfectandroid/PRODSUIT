package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadSourseModel
import com.perfect.prodsuit.Model.StockListCategoryModel
import com.perfect.prodsuit.Repository.StockListCategoryRepository
import com.perfect.prodsuit.Repository.leadSourseRepository


class LeadSourseViewModel : ViewModel() {

    var leadSourseData: MutableLiveData<LeadSourseModel>? = null

    fun getLeadSourse(context: Context) : LiveData<LeadSourseModel>? {
        leadSourseData = leadSourseRepository.getServicesApiCall(context)
        return leadSourseData
    }
}