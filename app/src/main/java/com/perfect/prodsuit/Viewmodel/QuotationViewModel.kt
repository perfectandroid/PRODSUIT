package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.QuotationModel
import com.perfect.prodsuit.Repository.QuotationRepository

class QuotationViewModel: ViewModel() {

    var quotationData: MutableLiveData<QuotationModel>? = null
    fun getInfo(context: Context) : LiveData<QuotationModel>? {
        quotationData = QuotationRepository.getServicesApiCall(context)
        return quotationData
    }
}