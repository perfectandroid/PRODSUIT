package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadStagesDashModel
import com.perfect.prodsuit.Model.LeadStatusDashModel
import com.perfect.prodsuit.Model.LeadstagewiseModel
import com.perfect.prodsuit.Model.top10chartModel
import com.perfect.prodsuit.Repository.LeadStagesForecastRepository
import com.perfect.prodsuit.Repository.LeadStatusDashRepository
import com.perfect.prodsuit.Repository.Top10ProductsRepository

class Top10LeadViewModel : ViewModel() {

    var top10LeadData: MutableLiveData<top10chartModel>? = null
    fun getLeadTop10Products(context: Context) : LiveData<top10chartModel>? {
        top10LeadData = Top10ProductsRepository.getServicesApiCall(context)
        return top10LeadData
    }
}