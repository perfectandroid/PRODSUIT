package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.NotifReadModel
import com.perfect.prodsuit.Repository.NotifctnReadRepository

class NotificationReadStatusViewModel : ViewModel() {

    var notifreadstatusLiveData: MutableLiveData<NotifReadModel>? = null

    fun getNotifreadstatus(context: Context) : LiveData<NotifReadModel>? {
        notifreadstatusLiveData = NotifctnReadRepository.getServicesApiCall(context)
        return notifreadstatusLiveData
    }

}