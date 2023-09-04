package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CurrentModel
import com.perfect.prodsuit.Model.ImageModeModel
import com.perfect.prodsuit.Repository.CurrentStatusRepository
import com.perfect.prodsuit.Repository.ImageModeRepository

class CurrentStatusViewModel : ViewModel()  {

    var currentstatusData: MutableLiveData<CurrentModel>? = null

    fun getCurrentStatus(context: Context) : LiveData<CurrentModel>? {
        currentstatusData = CurrentStatusRepository.getServicesApiCall(context)
        return currentstatusData
    }

}