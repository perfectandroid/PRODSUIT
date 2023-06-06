package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.FollowupStatusUpdateModel
import com.perfect.prodsuit.Repository.FollowupStatusUpdateRepository

class FollowupStatusUpdateViewModel  : ViewModel() {

    var followupStatusUpdateLiveData: MutableLiveData<FollowupStatusUpdateModel>? = null

    fun getFollowupStatusUpdate(context: Context,TransMode : String,customer_service_register : String,latitude : String,longitude : String,
                                address : String,curDate : String, curTime : String,journeyType : String) : LiveData<FollowupStatusUpdateModel>? {
        followupStatusUpdateLiveData = FollowupStatusUpdateRepository.getServicesApiCall(context,TransMode,customer_service_register,latitude,longitude,address,curDate, curTime,journeyType)
        return followupStatusUpdateLiveData
    }

}