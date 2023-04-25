package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ActivityListModel
import com.perfect.prodsuit.Repository.ActivityListRepository

class ActivityListViewModel : ViewModel() {

    var activitylistLiveData: MutableLiveData<ActivityListModel>? = null

    fun getActivitylist(context: Context, ID_LeadGenerateProduct :  String,ID_ActionType :  String) : LiveData<ActivityListModel>? {
        activitylistLiveData = ActivityListRepository.getServicesApiCall(context,ID_LeadGenerateProduct,ID_ActionType)
        return activitylistLiveData
    }

}