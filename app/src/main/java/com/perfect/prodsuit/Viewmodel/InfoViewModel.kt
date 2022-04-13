package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.InfoModel
import com.perfect.prodsuit.Repository.InfoRepository

class InfoViewModel: ViewModel() {

    var InfoData: MutableLiveData<InfoModel>? = null
    fun getInfo(context: Context) : LiveData<InfoModel>? {
        InfoData = InfoRepository.getServicesApiCall(context)
        return InfoData
    }
}