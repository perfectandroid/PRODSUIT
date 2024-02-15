package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.SheduleModel
import com.perfect.prodsuit.Repository.SheduleRepository

class SheduleViewModel: ViewModel() {

    var sheduleData: MutableLiveData<SheduleModel>? = null

    fun getShedule(context: Context ,ID_BranchType : String ) : LiveData<SheduleModel>? {
        sheduleData = SheduleRepository.getServicesApiCall(context,ID_BranchType)
        return sheduleData
    }

}