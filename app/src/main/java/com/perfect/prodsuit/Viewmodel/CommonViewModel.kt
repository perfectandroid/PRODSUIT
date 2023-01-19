package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CommonModel
import com.perfect.prodsuit.Repository.CommonRepository

class CommonViewModel : ViewModel()  {

    var channelData: MutableLiveData<CommonModel>? = null

    fun getCommonViewModel(context: Context,ReqMode : String,SubMode : String) : LiveData<CommonModel>? {
        channelData = CommonRepository.getServicesApiCall(context,ReqMode,SubMode)
        return channelData
    }
}