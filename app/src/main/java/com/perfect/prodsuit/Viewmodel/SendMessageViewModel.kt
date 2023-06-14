package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.SendMessageModel
import com.perfect.prodsuit.Repository.SendMessageRepository

class SendMessageViewModel : ViewModel() {

    var sendmessageData: MutableLiveData<SendMessageModel>? = null

    fun sendmessage(context: Context,messageTitle :String,messageDesc: String,Reciever_Id :String) : LiveData<SendMessageModel>? {
        sendmessageData = SendMessageRepository.getServicesApiCall(context,messageTitle,messageDesc,Reciever_Id)
        return sendmessageData
    }
}