package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ChannelModel
import com.perfect.prodsuit.Repository.ChannelRepository

class ChannelViewModel: ViewModel() {

    var channelData: MutableLiveData<ChannelModel>? = null

    fun getChannel(context: Context ,ID_BranchType : String ) : LiveData<ChannelModel>? {
        channelData = ChannelRepository.getServicesApiCall(context,ID_BranchType)
        return channelData
    }

}