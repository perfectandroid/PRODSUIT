package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ChatRegisterUserListModel
import com.perfect.prodsuit.Repository.ChatRegisterUserListRepository

class ChatRegisterUserListViewModel : ViewModel() {

    var chatRegisterUserListData: MutableLiveData<ChatRegisterUserListModel>? = null
    fun getChatRegisterUser(context: Context, ReqMode : String) : LiveData<ChatRegisterUserListModel>? {
        chatRegisterUserListData = ChatRegisterUserListRepository.getServicesApiCall(context,ReqMode)
        return chatRegisterUserListData
    }
}