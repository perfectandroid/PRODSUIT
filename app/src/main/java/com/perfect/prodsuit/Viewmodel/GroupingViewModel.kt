package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.GroupingModel
import com.perfect.prodsuit.Repository.GroupingRepository

class GroupingViewModel : ViewModel()  {

    var groupingData: MutableLiveData<GroupingModel>? = null

    fun getGrouping(context: Context) : LiveData<GroupingModel>? {
        groupingData = GroupingRepository.getServicesApiCall(context)
        return groupingData
    }
}