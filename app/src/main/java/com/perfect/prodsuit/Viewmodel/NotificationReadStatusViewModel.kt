package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.BannerModel
import com.perfect.prodsuit.Model.NotifReadModel
import com.perfect.prodsuit.Model.NotificationModel
import com.perfect.prodsuit.Repository.BannersRepository
import com.perfect.prodsuit.Repository.NotifctnReadRepository
import com.perfect.prodsuit.Repository.NotificationRepository

class NotificationReadStatusViewModel : ViewModel() {

    var notifreadstatusLiveData: MutableLiveData<NotifReadModel>? = null

    fun getNotifreadstatus(context: Context) : LiveData<NotifReadModel>? {
        notifreadstatusLiveData = NotifctnReadRepository.getServicesApiCall(context)
        return notifreadstatusLiveData
    }

}