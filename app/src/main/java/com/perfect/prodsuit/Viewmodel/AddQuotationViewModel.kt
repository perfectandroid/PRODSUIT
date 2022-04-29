package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AddQuotationModel
import com.perfect.prodsuit.Repository.AddQuotationRepository

class AddQuotationViewModel : ViewModel() {

    var addquotationLiveData: MutableLiveData<AddQuotationModel>? = null

    fun getAddquotation(context: Context) : LiveData<AddQuotationModel>? {
        addquotationLiveData = AddQuotationRepository.getServicesApiCall(context)
        return addquotationLiveData
    }

}