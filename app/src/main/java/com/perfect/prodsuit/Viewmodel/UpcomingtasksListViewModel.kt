package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.BannerModel
import com.perfect.prodsuit.Model.UpcomingtasksModel
import com.perfect.prodsuit.Repository.UpcomingtasksRepository

class UpcomingtasksListViewModel : ViewModel() {

    var upcomingtaskslistLiveData: MutableLiveData<UpcomingtasksModel>? = null

    fun getUpcomingtasklist(context: Context,submode : String, name : String, criteria : String, date : String) : LiveData<UpcomingtasksModel>? {
        upcomingtaskslistLiveData = UpcomingtasksRepository.getServicesApiCall(context,submode,name,criteria,date)
        return upcomingtaskslistLiveData
    }

}