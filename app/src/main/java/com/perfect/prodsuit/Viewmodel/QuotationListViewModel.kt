package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.QuotationListModel
import com.perfect.prodsuit.Repository.QuotatnListRepository

class QuotationListViewModel : ViewModel() {

    var quotatnlistLiveData: MutableLiveData<QuotationListModel>? = null

    fun getQuotationlist(context: Context) : LiveData<QuotationListModel>? {
        quotatnlistLiveData = QuotatnListRepository.getServicesApiCall(context)
        return quotatnlistLiveData
    }

}