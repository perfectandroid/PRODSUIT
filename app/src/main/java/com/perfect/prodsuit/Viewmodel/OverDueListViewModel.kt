package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.OverDueModel
import com.perfect.prodsuit.Repository.OverDueRepository

class OverDueListViewModel : ViewModel() {

    var overduelistLiveData: MutableLiveData<OverDueModel>? = null

    fun getOverduelist(context: Context) : LiveData<OverDueModel>? {
        overduelistLiveData = OverDueRepository.getServicesApiCall(context)
        return overduelistLiveData
    }

}