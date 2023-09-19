package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CorrectionSplitupModel

import com.perfect.prodsuit.Repository.CorrectionSplitupRepository

class CorrectionSplitupViewModel  : ViewModel()  {

    var correctionSplitupData: MutableLiveData<CorrectionSplitupModel>? = null

    fun getCorrectionSplitup(context: Context, TransMode : String) : MutableLiveData<CorrectionSplitupModel>? {
        correctionSplitupData = CorrectionSplitupRepository.getServicesApiCall(context,TransMode)
        return correctionSplitupData
    }
}