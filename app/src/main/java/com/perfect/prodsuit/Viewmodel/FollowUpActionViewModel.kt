package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.FollowUpActionModel
import com.perfect.prodsuit.Repository.FollowUpActionRepository

class FollowUpActionViewModel: ViewModel()  {

    var followupactionData: MutableLiveData<FollowUpActionModel>? = null
    fun getFollowupAction(context: Context) : LiveData<FollowUpActionModel>? {
        followupactionData = FollowUpActionRepository.getServicesApiCall(context)
        return followupactionData
    }

}