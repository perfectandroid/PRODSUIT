package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ActivityListModel
import com.perfect.prodsuit.Model.DeleteLeadModel
import com.perfect.prodsuit.Repository.ActivityListRepository
import com.perfect.prodsuit.Repository.DeleteLeadRepository

class DeleteLeadViewModel : ViewModel() {

    var deleteleadLiveData: MutableLiveData<DeleteLeadModel>? = null

    fun getDeletelead(context: Context) : LiveData<DeleteLeadModel>? {
        deleteleadLiveData = DeleteLeadRepository.getServicesApiCall(context)
        return deleteleadLiveData
    }

}