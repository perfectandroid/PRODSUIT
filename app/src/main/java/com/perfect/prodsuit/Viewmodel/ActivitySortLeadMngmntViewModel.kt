package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ActivityListModel
import com.perfect.prodsuit.Model.AddSortLeadmngmntModel
import com.perfect.prodsuit.Repository.ActivityListRepository
import com.perfect.prodsuit.Repository.SortLeadMangeListRepository

class ActivitySortLeadMngmntViewModel : ViewModel() {

    var sortleadmngmntlistLiveData: MutableLiveData<AddSortLeadmngmntModel>? = null

    fun getSortlist(context: Context) : LiveData<AddSortLeadmngmntModel>? {
        sortleadmngmntlistLiveData = SortLeadMangeListRepository.getServicesApiCall(context)
        return sortleadmngmntlistLiveData
    }

}