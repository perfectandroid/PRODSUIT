package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ActivityListModel
import com.perfect.prodsuit.Model.AddNoteModel
import com.perfect.prodsuit.Model.BannerModel
import com.perfect.prodsuit.Repository.ActivityListRepository
import com.perfect.prodsuit.Repository.AddNoteRepository
import com.perfect.prodsuit.Repository.BannersRepository

class ActivityListViewModel : ViewModel() {

    var activitylistLiveData: MutableLiveData<ActivityListModel>? = null

    fun getActivitylist(context: Context) : LiveData<ActivityListModel>? {
        activitylistLiveData = ActivityListRepository.getServicesApiCall(context)
        return activitylistLiveData
    }

}