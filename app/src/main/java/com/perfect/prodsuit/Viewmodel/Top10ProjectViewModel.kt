package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.Top10ProjectModel
import com.perfect.prodsuit.Repository.Top10ProjectRepository

class Top10ProjectViewModel : ViewModel() {

    var top10ProjectData: MutableLiveData<Top10ProjectModel>? = null
    fun getTop10Project(context: Context) : LiveData<Top10ProjectModel>? {
        top10ProjectData = Top10ProjectRepository.getServicesApiCall(context)
        return top10ProjectData
    }
}