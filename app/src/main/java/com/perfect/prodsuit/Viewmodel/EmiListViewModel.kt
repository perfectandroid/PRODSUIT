package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.EmiListModel
import com.perfect.prodsuit.Repository.EmiListRepository

class EmiListViewModel  : ViewModel() {
    var emiListLiveData: MutableLiveData<EmiListModel>? = null

    fun getEmiList(context: Context) : LiveData<EmiListModel>? {
        emiListLiveData = EmiListRepository.getEmiList(context)
        return emiListLiveData
    }
}