package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.JobcardsModel
import com.perfect.prodsuit.Repository.ItemSearchListRepository
import com.perfect.prodsuit.Repository.JobcardsRespository

class JobcardsViewModel : ViewModel() {

    var jobcardsData: MutableLiveData<JobcardsModel>? = null

    fun getJobcards(context: Context, TransDate : String, DashMode : String, DashType : String) : LiveData<JobcardsModel>? {
        jobcardsData = JobcardsRespository.getServicesApiCall(context,TransDate,DashMode,DashType)
        return jobcardsData
    }
}