package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.FollowUpTypeModel
import com.perfect.prodsuit.Repository.FollowUpTypeRepository

class FollowUpTypeViewModel: ViewModel() {

    var followuptypeData: MutableLiveData<FollowUpTypeModel>? = null
    fun getFollowupType(context: Context) : LiveData<FollowUpTypeModel>? {
        followuptypeData = FollowUpTypeRepository.getServicesApiCall(context)
        return followuptypeData
    }
}