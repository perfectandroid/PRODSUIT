package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.NotificationModel
import com.perfect.prodsuit.Repository.NotificationRepository

class NotificationViewModel : ViewModel() {

    var notificationlistLiveData: MutableLiveData<NotificationModel>? = null

    fun getNotificaationlist(context: Context) : LiveData<NotificationModel>? {
        notificationlistLiveData = NotificationRepository.getServicesApiCall(context)
        return notificationlistLiveData
    }

}