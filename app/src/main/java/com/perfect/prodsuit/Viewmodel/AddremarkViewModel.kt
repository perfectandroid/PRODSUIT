package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ActionListTicketReportModel
import com.perfect.prodsuit.Model.ActivityListModel
import com.perfect.prodsuit.Model.AddRemarkModel
import com.perfect.prodsuit.Repository.ActionListTicketReportRepository
import com.perfect.prodsuit.Repository.ActivityListRepository
import com.perfect.prodsuit.Repository.AddremarkRepository

class AddremarkViewModel : ViewModel() {

    var addRemarkLiveData: MutableLiveData<AddRemarkModel>? = null

    fun getAddremark(context: Context) : LiveData<AddRemarkModel>? {
        addRemarkLiveData = AddremarkRepository.getServicesApiCall(context)
        return addRemarkLiveData
    }

}