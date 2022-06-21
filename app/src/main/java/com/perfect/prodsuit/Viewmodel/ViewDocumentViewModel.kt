package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ViewDocumentModel
import com.perfect.prodsuit.Repository.ViewDocumentRepository

class ViewDocumentViewModel : ViewModel() {

    var viewDocumentData: MutableLiveData<ViewDocumentModel>? = null
    fun getViewDocument(context: Context, ID_LeadGenerate : String, ID_LeadGenerateProduct : String,ID_LeadDocumentDetails : String) : MutableLiveData<ViewDocumentModel>? {
        viewDocumentData = ViewDocumentRepository.getServicesApiCall(context,ID_LeadGenerate,ID_LeadGenerateProduct,ID_LeadDocumentDetails)
        return viewDocumentData
    }
}