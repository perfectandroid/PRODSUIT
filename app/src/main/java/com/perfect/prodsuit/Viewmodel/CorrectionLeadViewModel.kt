package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CorrectionLeadModel
import com.perfect.prodsuit.Repository.CorrectionLeadRepository

class CorrectionLeadViewModel : ViewModel()  {

    var correctionLeadData: MutableLiveData<CorrectionLeadModel>? = null

    fun getCorrectionLead(context: Context) : LiveData<CorrectionLeadModel>? {
        correctionLeadData = CorrectionLeadRepository.getServicesApiCall(context)
        return correctionLeadData
    }

}