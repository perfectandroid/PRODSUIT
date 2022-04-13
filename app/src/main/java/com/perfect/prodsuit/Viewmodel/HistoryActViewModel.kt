package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.HistoryActModel
import com.perfect.prodsuit.Repository.HistoryActRepository

class HistoryActViewModel: ViewModel() {

    var historyActData: MutableLiveData<HistoryActModel>? = null
    fun getHistoryAct(context: Context) : LiveData<HistoryActModel>? {
        historyActData = HistoryActRepository.getServicesApiCall(context)
        return historyActData
    }
}