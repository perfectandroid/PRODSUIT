package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CommonSearchListModel
import com.perfect.prodsuit.Repository.CommonSearchListRepository

class CommonSearchListViewModel : ViewModel() {

    var commonSearchListData: MutableLiveData<CommonSearchListModel>? = null

    fun getCommonSearchList(context: Context) : LiveData<CommonSearchListModel>? {
        commonSearchListData = CommonSearchListRepository.getServicesApiCall(context)
        return commonSearchListData
    }
}