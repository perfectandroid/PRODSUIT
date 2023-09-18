package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CorrectionModuleListModel
import com.perfect.prodsuit.Repository.CorrectionModuleListRepository

class CorrectionModuleListViewModel : ViewModel()  {

    var correctionmodulelistData: MutableLiveData<CorrectionModuleListModel>? = null

    fun getCorrectionModuleList(context: Context) : LiveData<CorrectionModuleListModel>? {
        correctionmodulelistData = CorrectionModuleListRepository.getServicesApiCall(context)
        return correctionmodulelistData
    }

}